import org.springframework.mock.web.MockMultipartFile
import org.springframework.mock.web.MockMultipartHttpServletRequest

class SunPetstoreImporterService {

    static transactional = true
    def exportFileName = new File("scripts/sun_petstore_export.xml")
    def imageDirectory = "../../javapetstore-2.0-ea5/web/"

    def importProductsAndCategories() {
        def petstore = new XmlSlurper().parse(exportFileName)

        petstore.categories.category.each { c ->
            def cName = c.name.text()
            def category = Category.findByName(cName)
            if (!category) {
                category = new Category(
                    name:cName,
                    description:c.description.text(),
                    imageUrl:c.imageurl.text()
                )
                println "Stored category $cName"
            }

            c.products.product.each { p ->
                def pName = p.name.text()
                def product = Product.findByName(pName)
                if (!product) {
                    product = new Product(
                            name:pName,
                            description:p.description.text(),
                            imageUrl:p.imageurl.text(),
                    )
                }
                category.addToProducts(product)
                println "Added product $pName to category $cName"
            }
            assert category.save()
        }

        println "Imported categories: " + Category.count()
        println "Imported products: " + Product.count()

        println "About to import ${petstore.items.item.size()} items"

        assert new File(imageDirectory).exists()

        def requests = []
        petstore.items.item.each { i ->
            def product = Product.findByName(i.product.name.text())
            def request = new MockMultipartHttpServletRequest()

            // Item properties
            i.children().findAll {
                it.name() in ["name","description","price","totalScore","numberOfVotes"] }.each {

                request.addParameter(it.name(), it.text())
            }
            // Product
            request.addParameter(
                "product.id",
                Product.findByName(i.product.text()).id.toString()
            )
            // Contact info
            i.contactinfo.children().each {
                request.addParameter("contactInfo." + it.name(), it.text())
            }
            // Address
            i.address.children().each {
                request.addParameter("address." + it.name(), it.text())
            }
            // Tags
            request.addParameter("tags", i.tags.tag.collect{ it.text() }.join(" "))
            // Image
            def image = i.image.text()
            def imageFile = new File(imageDirectory, image)
            assert imageFile.exists()
            def mpFile = new MockMultipartFile(
                image.substring("images/".length()),
                imageFile.readBytes()
            )
            request.addFile(mpFile)

            println "Parameters: " + request.parameterMap
            println "Files: " + request.fileMap

            // TODO: dispatch request to controller  
        }
    }

}