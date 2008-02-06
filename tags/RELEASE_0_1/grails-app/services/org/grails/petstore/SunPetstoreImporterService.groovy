package org.grails.petstore

class SunPetstoreImporterService {

    static transactional = true

    def exportFileName = new File("scripts/sun_petstore_export.xml")
    def imageDirectory = "../javapetstore-2.0-ea5/web/"
    def imageStorageService

    def importProductsAndCategories() {
        def petstore = new XmlSlurper().parse(exportFileName)

        petstore.categories.category.each { c ->
            def cName = c.name.text()
            def category = Category.findByName(cName)
            if (!category) {
                def imageUrl = c.imageurl.text()
                def format = imageUrl.substring(imageUrl.lastIndexOf('.') + 1)
                def image = new File(imageDirectory, imageUrl)
                assert image.exists()

                def imageName = cName + "." + format
                imageStorageService.storeCategoryImage(imageName, image.readBytes())

                category = new Category(
                    name:cName,
                    description:c.description.text(),
                    imageUrl:imageName
                )

                log.debug "Stored category $cName"
            }

            c.products.product.each { p ->
                def pName = p.name.text()
                def product = Product.findByName(pName)
                if (!product) {
                    def imageUrl = p.imageurl.text()
                    def format = imageUrl.substring(imageUrl.lastIndexOf('.') + 1)
                    def image = new File(imageDirectory, imageUrl)
                    assert image.exists()

                    def imageName = pName + "." + format
                    imageStorageService.storeProductImage(imageName, image.readBytes())

                    product = new Product(
                            name:pName,
                            description:p.description.text(),
                            imageUrl:imageName,
                    )
                }
                category.addToProducts(product)
                log.debug "Added product $pName to category $cName"
            }
            assert category.save()
        }

        log.info "Imported ${Category.count()} categories and ${Product.count()} products"
    }

    def importItems() {
        def petstore = new XmlSlurper().parse(exportFileName)

        log.info "About to import ${petstore.items.item.size()} items"

        assert new File(imageDirectory).exists()

        petstore.items.item.each { itemTag ->
            // Item
            def item = new Item(
                name:itemTag.name.text(),
                description:itemTag.description.text(),
                price:Float.valueOf(itemTag.price.text()).intValue(),
                totalScore:Integer.valueOf(itemTag.score.text()),
                numberOfVotes:Integer.valueOf(itemTag.votes.text())
            )

            // Product
            item.product = Product.findByName(itemTag.product.text())


            // Contact info
            def contactInfo = new SellerContactInfo()
            def ci = itemTag.contactinfo
            contactInfo.firstName =  ci.firstname
            contactInfo.lastName = ci.lastname
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


            // Tags
            def tagStrings = itemTag.tags.tag.collect { it.text() }
            item.tag(tagStrings)

            // Image
            def image = itemTag.image.text()
            def imageFile = new File(imageDirectory, image)
            item.imageUrl =  imageStorageService.storeUploadedImage(imageFile.readBytes(), "image/jpeg")

            assert item.save()
            log.debug "Saved item ${item.id}"
            print "."
        }
        println ""
        log.info "Imported ${petstore.items.item.size()} items!"
    }

}