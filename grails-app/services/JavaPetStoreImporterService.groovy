import java.util.concurrent.LinkedBlockingQueue
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.springframework.core.io.Resource

class JavaPetStoreImporterService {

    static transactional = true

    SearchableService searchableService
    ImageStorageService imageStorageService
    ItemService itemService
    Resource exportFileResource

    def importCategory = { c ->
        def cName = c.name.text()
        def category = Category.findByName(cName)
        if (!category) {
            category = new Category(
                name:cName,
                description:c.description.text()
            )
        }
        return category
    }

    def importProduct = { p ->
        def pName = p.name.text()
        def product = Product.findByName(pName)
        if (!product) {
            product = new Product(
                    name:pName,
                    description:p.description.text()
            )
        }
        return product
    }

    def importItem = { itemTag ->
        // Item
        def item = new Item(
            name:itemTag.name.text(),
            description:itemTag.description.text(),
            price:itemTag.price.text().toFloat().round(),
            totalScore:itemTag.totalScore.text().toInteger(),
            numberOfVotes:itemTag.numberOfVotes.text().toInteger()
        )

        // Product
        item.product = Product.findByName(itemTag.product.text())

        // Contact info
        def contactInfo = new SellerContactInfo()
        def ci = itemTag.contactInfo
        contactInfo.firstName =  ci.firstName
        contactInfo.lastName = ci.lastName
        contactInfo.email = ci.email
        item.contactInfo = contactInfo

        // Address
        def address = new Address()
        def adr = itemTag.address
        address.street1 = adr.street1
        address.street2 = adr.street2
        address.city = adr.city
        address.state = adr.state
        address.zip = adr.zip
        item.address = address

        // Image
        def imageBytes = itemTag.image.text().decodeBase64()
        item.imageUrl =  imageStorageService.storeUploadedImage(imageBytes, "image/jpeg")

        return item
    }

    void importProductsAndCategories() {
        def petstore = new XmlSlurper().parse(exportFileResource.file)
        def categories = petstore.categories.category

        log.info "About to import ${categories.size()} categories " +
                 "and ${categories.products.product.size()} products."

        categories.each { c ->
            def category = importCategory(c)
            c.products.product.each { p ->
                def product = importProduct(p)
                category.addToProducts(product)
            }
            assert category.save()
        }

        log.info "Imported ${Category.count()} categories and ${Product.count()} products."
    }

    void importItems() {
        def petstore = new XmlSlurper().parse(exportFileResource.file)

        def queue = new LinkedBlockingQueue()

        def itemCount = ConfigurationHolder.config.jpsImport.maxItems
        log.info "About to import ${itemCount ?: "all"} items."

        def startTime = System.currentTimeMillis()


        // Producer thread
        Thread.start {
            def lastItem = (itemCount ?: 0) - 1
            petstore.items.item[0..lastItem].each { itemTag ->
                def item = importItem(itemTag)
                def tagList = itemTag.tags.tag.collect { it.text() }
                queue.add(new ImportQueueElement(item:item,tagList:tagList))
            }
            queue.add(ImportQueueElement.EOS)
        }

        // Consumer loop
        def next
        while ((next = queue.take()) != ImportQueueElement.EOS) {
            itemService.tagAndSave(next.item, next.tagList)
        }

        log.info "Imported ${Item.count()} items in ${System.currentTimeMillis() - startTime} ms"
    }                                                                                               

}
