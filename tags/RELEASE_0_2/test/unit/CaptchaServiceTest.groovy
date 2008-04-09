import javax.imageio.ImageIO

class CaptchaServiceTest extends GroovyTestCase {

    void testGetImage() {
        def captchaService = new CaptchaService()
        captchaService.setCaptchaString()
        def image = captchaService.getCaptchaImage()
        def baos = new ByteArrayOutputStream()
        ImageIO.write(image, "jpeg", baos)

        int len = baos.toByteArray().length
        assert len > 0
    }

}