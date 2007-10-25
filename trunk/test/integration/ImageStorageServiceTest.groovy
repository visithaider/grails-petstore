import org.springframework.core.io.ClassPathResource
import org.springframework.mock.web.MockMultipartFile

class ImageStorageServiceTest extends GroovyTestCase {

    void testStoreImage() {
        ImageStorageService imageStorageService = new ImageStorageService()

        def res = new ClassPathResource("test/resources/test.jpg")
        MockMultipartFile file = new MockMultipartFile("test", res.inputStream)
        file.contentType = "image/jpg"

        def path = imageStorageService.storeUploadedImage(file)

        assert path != null
        assert path =~ /.*\.jpg$/

        file.contentType = "not/supported"
        try {
            imageStorageService.storeUploadedImage(file)
            fail "Should not be possible to store images with content type $contentType"
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

}