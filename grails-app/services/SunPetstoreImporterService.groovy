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

                imageStorageService.storeCategoryImage(cName, format, image.readBytes())

                category = new Category(
                    name:cName,
                    description:c.description.text(),
                    imageUrl:image.name
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

                    imageStorageService.storeProductImage(pName, format, image.readBytes())

                    product = new Product(
                            name:pName,
                            description:p.description.text(),
                            imageUrl:image.name,
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
            item.contactInfo = new SellerContactInfo()
            def ci = itemTag.contactinfo
            item.contactInfo.firstName =  ci.firstname
            item.contactInfo.lastName = ci.lastname
            item.contactInfo.email = ci.email

            // Address
            item.address = new Address()
            def adr = itemTag.address
            item.address.street1 = adr.street1
            item.address.street2 = adr.street2
            item.address.city = adr.city
            item.address.state = adr.state
            item.address.zip = adr.zip

            // Tags
            def tagStrings = itemTag.tags.tag.collect { it.text() }
            item.tag(tagStrings)

            // Image
            def image = itemTag.image.text()
            def imageFile = new File(imageDirectory, image)
            assert imageFile.exists()
            imageStorageService.storeUploadedImage(imageFile.readBytes(), "image/jpeg")

            assert item.save()
            log.debug "Saved item " + item.id
        }
        log.info "Imported ${petstore.items.item.size()} items!"

    }

}