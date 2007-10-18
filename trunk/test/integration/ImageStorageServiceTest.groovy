import org.springframework.core.io.ClassPathResource
import org.springframework.mock.web.MockMultipartFile

class ImageStorageServiceTest extends GroovyTestCase {

    void testStoreImage() {
        ImageStorageService imageStorageService = new ImageStorageService()          
        def res = new ClassPathResource("test.jpg")
        MockMultipartFile file = new MockMultipartFile("test", res.inputStream)
        file.contentType = "image/jpg"

        def path = imageStorageService.storeUploadedImage(file)

        assert path != null
        assert path =~ /.*\.jpg$/

        file.contentType = "not/supported"
        try {
            imageStorageService.storeUploadedImage(file)
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

}