package gps

import javax.imageio.ImageIO

class CaptchaController {

    def captchaService

    def index = {
        def image = captchaService.generateCaptchaImage()
        response.contentType = "application/jpeg"
        ImageIO.write(image, "jpeg", response.outputStream)
    }

}

