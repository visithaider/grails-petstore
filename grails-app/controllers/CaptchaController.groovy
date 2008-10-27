import javax.imageio.ImageIO

class CaptchaController {

    CaptchaService captchaService

    def index = {
        def image = captchaService.generateCaptchaImage()
        response.contentType = "application/jpeg"
        ImageIO.write(image, "jpeg", response.outputStream)
    }

}

