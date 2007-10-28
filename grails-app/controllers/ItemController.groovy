import javax.imageio.ImageIO
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage

class ItemController {

    GeoCoderService geoCoderService
    CaptchaService captchaService
    ImageStorageService imageStorageService

    static final String CAPTCHA_ATTR = "captchaString"

    def scaffold = Item
    def defaultAction = "list"

    def setCaptcha = {
        session[CAPTCHA_ATTR] = captchaService.generateCaptchaString(6)
    }

    def unsetCaptcha = {
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
        if (params.price) {
            item.price = params.price.toInteger()
        }
        if (params.imageURL) {
            item.imageURL = params.imageURL
        }

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
        def category = Category.get(params["product.category.id"])
        category.addToProducts(product)
        product.item = item

        item.product = product

        SellerContactInfo contactInfo = new SellerContactInfo()
        ["firstName","lastName","email"].each {
            contactInfo.setProperty(it, params["contactInfo." + it])
        }
        item.contactInfo = contactInfo

        params.tagNames?.split("\\s").each {
            Tag tag = Tag.findByTag(it)
            if (tag) {
                item.addToTags(tag)
            } else {
                item.addToTags(new Tag(tag:it))
            }
        }

        def uploaded = request.getFile("file")
        if (!uploaded.empty) {
            imageStorageService.deleteImage(item.imageURL)
            item.imageURL = imageStorageService.storeUploadedImage(uploaded)
        }

        item.validate()
        if (session[CAPTCHA_ATTR] && (params[CAPTCHA_ATTR]?.trim() != session[CAPTCHA_ATTR])) {
            item.errors.reject("captchaMismatch")
        }
        if (!item.errors.hasErrors() && item.save()) {
            flash.message = "Saved item with id = " + item.id
            unsetCaptcha()
            redirect(action:show,id:item.id)
        } else {
            flash.message = "${item.errors.errorCount} validation errors."
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

    def voteFor = {
        if (params.id && params.rating) {
            def item = Item.get(params.id)
            item.addRating(params.rating.toInteger())
            item.save()
            flash.message = "You rated ${item.product.name} as ${params.rating}"
            redirect(action:show,id:params.id)
        } else {
            redirect(action:list)
        }
    }
}