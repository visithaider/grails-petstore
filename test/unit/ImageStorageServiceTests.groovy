import org.springframework.mock.web.MockMultipartFile
import org.springframework.mock.web.MockServletContext

class ImageStorageServiceTests extends GroovyTestCase {

    def imageStorageService = new ImageStorageService()
    //def res = new FileSystemResource("test/resources/test.jpg")

    def file = new MockMultipartFile("test.jpg", "".getBytes())
    def sc = new MockServletContext("test/resources")

    protected void setUp() {
        imageStorageService.servletContext = sc
        imageStorageService.afterPropertiesSet()
        println "HEJ: " + imageStorageService.thumbnailDir
    }

    void testStoreUploadedImage() {

        def path = imageStorageService.storeUploadedImage(file.bytes, "image/jpg")

        assert path != null
        assert path =~ /.*\.jpg$/

        try {
            imageStorageService.storeUploadedImage(file.bytes, "not/supported")
            fail "Should not be possible to store images with content type $contentType"
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    void testStoreProductImage() {
        imageStorageService.storeProductImage(this.file.name, this.file.bytes)

        assert new File(imageStorageService.productDir, file.name).exists()
    }

    void testStoreCategoryImage() {
        imageStorageService.storeCategoryImage(this.file.name, this.file.bytes)

        assert new File(imageStorageService.categoryDir, file.name).exists()

    }

    protected void tearDown() {
        imageStorageService.clearDirectories()
    }


}