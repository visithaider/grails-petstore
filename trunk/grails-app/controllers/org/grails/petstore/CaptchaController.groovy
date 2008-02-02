package org.grails.petstore

import javax.imageio.ImageIO

class CaptchaController {

    def captchaService

    def index = {
        def captcha = session[ItemController.CAPTCHA_ATTR]
        def image = captchaService.getCaptchaImage(captcha)
        response.contentType = "application/jpeg"
        ImageIO.write(image, "jpeg", response.outputStream)
    }

}

