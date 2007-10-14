import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class CaptchaController {

    CaptchaService captchaService

    def index = {
        String str = session["captchaString"]
        BufferedImage image = captchaService.getCaptchaImage(str)
        response.contentType = "application/jpeg"
        ImageIO.write(image, "jpeg", response.outputStream)
    }

}

