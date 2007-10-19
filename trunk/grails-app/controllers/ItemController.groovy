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

    def setCaptcha() {
        session[CAPTCHA_ATTR] = captchaService.generateCaptchaString(6)
    }

    def unsetCaptcha() {
        session.removeAttribute(CAPTCHA_ATTR)
    }

    def edit = {
        println "Editing!"
        
        setCaptcha()
        [item:Item.get(params.id)]
    }

    def create = {
        setCaptcha()
        render(view:"edit", model:[item:new Item()])
    }

    def save = {
        def item = new Item(address:new Address(), contactInfo:new SellerContactInfo(),product:new Product())
        item.properties = params

        // TODO: need Grails 1.0 to bind more than one level deep (which sucks)
        ["city","state","street1","street2","zip"].each {
            item.address.setProperty(it, params["address." + it])
        }

        ["name","description"].each {
            item.product.setProperty(it, params["product." + it])
        }
        item.product.category = Category.get(params["product.category.id"])

        ["firstName","lastName","email"].each {
            item.contactInfo.setProperty(it, params["contactInfo." + it])
        }

        if (params[CAPTCHA_ATTR] != session[CAPTCHA_ATTR]) {
            item.errors.reject("captcha.mismatch")
            //assert !item.validate()
        }
        unsetCaptcha()

        def uploaded = request.getFile("file")
        if (!uploaded.empty) {
            imageStorageService.deleteImage(item.imageURL)
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