import javax.imageio.ImageIO

class CaptchaServiceTest extends GroovyTestCase {

    void testGetImage() {
        CaptchaService captcha = new CaptchaService()
        captcha.setCaptchaString()
        def image = captcha.getCaptchaImage()
        def baos = new ByteArrayOutputStream()
        ImageIO.write(image, "jpeg", baos)

        int len = baos.toByteArray().length
        assert len == 835
    }

}