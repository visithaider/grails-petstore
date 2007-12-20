import org.springframework.mock.web.MockMultipartHttpServletRequest
import org.springframework.mock.web.MockMultipartFile


def exportedFile = new File("sun_petstore_export.xml")
assert exportedFile.exists()

def petstore = new XmlSlurper().parse(exportedFile)

def imageDirectory = "../../../javapetstore-2.0-ea5/web/"
assert new File(imageDirectory).exists()

println "About to import ${petstore.items.item.size()} items"

def requests = []
petstore.items.item.each { i ->
    //def product = Product.findByName(i.product.name.text())
    def request = new MockMultipartHttpServletRequest()

    // Item properties
    i.children().findAll {
        it.name() in ["name","description","price","totalScore","numberOfVotes"] }.each {

        request.addParameter(it.name(), it.text())
    }
    // Product
    request.addParameter(
        "product.id", "TODO"
        //Product.findByName(i.product.text()).id.toString()
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
    //println "Files: " + request.fileMap

    // TODO: dispatch request to controller
}
