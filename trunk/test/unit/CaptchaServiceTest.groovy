import javax.imageio.ImageIO

class CaptchaServiceTest extends GroovyTestCase {

    void testGetImage() {
        CaptchaService captcha = new CaptchaService()
        def image = captcha.getCaptchaImage("ABC123")
        def baos = new ByteArrayOutputStream()
        ImageIO.write(image, "jpeg", baos)

        int len = baos.toByteArray().length
        assert len > 1000
    }

}