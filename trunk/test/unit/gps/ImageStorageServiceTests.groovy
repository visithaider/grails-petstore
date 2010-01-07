package gps

import javax.servlet.ServletContext
import org.springframework.core.io.ClassPathResource
import org.springframework.mock.web.MockMultipartFile

class ImageStorageServiceTests extends GroovyTestCase {

    def imageStorageService = new ImageStorageService()
    def testFile = new ClassPathResource("test.jpg")
    def file = new MockMultipartFile("test.jpg", testFile.inputStream)

    protected void setUp() {
        imageStorageService.servletContext = {"classes"} as ServletContext
        imageStorageService.afterPropertiesSet()
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
        imageStorageService.storeProductImage(file.name, file.bytes)

        assert new File(imageStorageService.productDir, file.name).exists()
    }

    void testStoreCategoryImage() {
        imageStorageService.storeCategoryImage(this.file.name, this.file.bytes)

        assert new File(imageStorageService.categoryDir, file.name).exists()

    }

    protected void tearDown() {
        imageStorageService.destroy()
    }


}