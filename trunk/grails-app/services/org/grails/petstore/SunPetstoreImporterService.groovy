package org.grails.petstore

import org.springframework.beans.factory.InitializingBean
import org.springframework.context.ResourceLoaderAware
import org.springframework.core.io.ResourceLoader

class SunPetstoreImporterService implements ResourceLoaderAware, InitializingBean {

    static transactional = true

    ImageStorageService imageStorageService
    ItemService itemService
    ResourceLoader resourceLoader

    File exportFile

    @Override
    void afterPropertiesSet() {
       exportFile = resourceLoader.getResource("classpath:sun_petstore_export.xml").file
    }

    def importCategory = { c ->
        def cName = c.name.text()
        def category = Category.findByName(cName)
        if (!category) {
            def imageUrl = c.imageurl.text()
            def format = imageUrl.substring(imageUrl.lastIndexOf('.') + 1)
            def imageName = cName + "." + format
            def imageBytes = c.image.text().decodeBase64()
            imageStorageService.storeCategoryImage(imageName, imageBytes)

            category = new Category(
                name:cName,
                description:c.description.text(),
                imageUrl:imageName
            )
        }
        return category
    }

    def importProduct = { p ->
        def pName = p.name.text()
        def product = Product.findByName(pName)
        if (!product) {
            def imageUrl = p.imageurl.text()
            def format = imageUrl.substring(imageUrl.lastIndexOf('.') + 1)
            def imageName = pName + "." + format
            def imageBytes = p.image.text().decodeBase64()
            imageStorageService.storeProductImage(imageName, imageBytes)

            product = new Product(
                    name:pName,
                    description:p.description.text(),
                    imageUrl:imageName,
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
        def petstore = new XmlSlurper().parse(exportFile)
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

    void importItems(maxItems) {
        def petstore = new XmlSlurper().parse(exportFile)

        log.info "About to import ${maxItems} items."

        petstore.items.item[0..maxItems].each { itemTag ->
            def item = importItem(itemTag)
            def tagList = itemTag.tags.tag.collect { it.text() }

            assert itemService.tagAndSave(item, tagList)
            print "."                       
        }
        println ""

        log.info "Imported ${Item.count()} items."
    }

}