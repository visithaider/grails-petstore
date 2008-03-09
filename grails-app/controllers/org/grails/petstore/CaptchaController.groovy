package org.grails.petstore

import javax.imageio.ImageIO

class CaptchaController {

    CaptchaService captchaService

    def index = {
        def image = captchaService.getCaptchaImage()
        response.contentType = "application/jpeg"
        ImageIO.write(image, "jpeg", response.outputStream)
    }

}

