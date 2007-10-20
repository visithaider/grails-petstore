import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class CaptchaController {

    CaptchaService captchaService

    def index = {
        String captcha = session[ItemController.CAPTCHA_ATTR]
        BufferedImage image = captchaService.getCaptchaImage(captcha)
        response.contentType = "application/jpeg"
        ImageIO.write(image, "jpeg", response.outputStream)
    }

}

