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
        setCaptcha()
        [item:Item.get(params.id)]
    }

    def create = {
        setCaptcha()
        render(view:"edit", model:[item:new Item()])
    }

    def save = {
        Item item
        if (params.id) {
            item = Item.get(params.id)
        } else {
            item = new Item()
        }
        item.price = 100
        

        //item.properties = params

        // TODO: need Grails 1.0 to bind more than one level deep
        Address address = new Address()
        ["city","state","street1","street2","zip"].each {
            address.setProperty(it, params["address." + it])
        }
        item.address = address

        Product product = new Product()
        ["name","description"].each {
            product.setProperty(it, params["product." + it])
        }
        product.category = Category.get(params["product.category.id"])
        item.product = product

        println "Product: " + item.product.properties

        SellerContactInfo contactInfo = new SellerContactInfo()
        ["firstName","lastName","email"].each {
            contactInfo.setProperty(it, params["contactInfo." + it])
        }
        item.contactInfo = contactInfo

        params.tagNames?.split("\\s").each {
            println "TAG: " + it
            Tag tag = Tag.findByTag(it)
            if (tag) {
                item.addToTags(tag)
            } else {
                item.addToTags(new Tag(tag:it))
            }
        }


        if (params[CAPTCHA_ATTR].trim() != session[CAPTCHA_ATTR]) {
            item.errors.reject("captcha.mismatch")
            //assert !item.validate()
        }
        //unsetCaptcha()

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
            flash.message = "Validation errors"
            render(view:"edit", model:[item:item])
        }
    }

    def delete = {
        Item item = Item.get(params.id)
        if (item) {
            item.delete()
            imageStorageService.deleteImage(item.imageURL)
        }
        flash.message = "Item ${item.id} deleted."
        redirect(action:list)
    }

}