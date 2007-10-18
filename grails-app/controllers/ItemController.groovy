import javax.imageio.ImageIO
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage

class ItemController {

    GeoCoderService geoCoderService
    CaptchaService captchaService
    ImageStorageService imageStorageService

    static final String CAPTCHA_ATTR = "captchaString"

    def scaffold = Item
    def defaultAction = "create"

    def create = {
        session[CAPTCHA_ATTR] = captchaService.generateCaptchaString(6)
        render(view:"edit", model:[item:new Item()])
    }

    def save = {
        if (params[CAPTCHA_ATTR] != session[CAPTCHA_ATTR]) {
            // TODO: add error
        }

        def item = new Item(address:new Address(), contactInfo:new SellerContactInfo(),product:new Product())
        item.properties = params
        // TODO: need Grails 1.0 to bind more than one level deep (which sucks)

        def uploaded = request.getFile("file")
        if (!uploaded.empty) {
            item.imageURL = imageStorageService.storeUploadedImage(uploaded)
        }

        // TODO: set latitude & longitude
        //List gp = geoCoderService.geoCode(item.toString())

        if (item.save()) {
            flash.message = "Saved item with id = " + item.id
            redirect(action:show,id:item.id)
        } else {
            render(view:"edit", model:[item:item])
        }
    }

}