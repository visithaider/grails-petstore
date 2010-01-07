package gps

import org.springframework.web.multipart.MultipartFile

class ItemCommand {

    Long id
    Product product
    Address address
    SellerContactInfo contactInfo
    String name, description, tagString, imageUrl, captcha
    Integer price
    MultipartFile file

    def imageStorageService
    def captchaService

    static constraints = {
        name(blank:false)
        price(blank:false,nullable:false,min:0)
    }

    ItemCommand() {}

    ItemCommand(Item item) {
        id = item.id
        product = item.product
        address = item.address
        contactInfo = item.contactInfo
        imageUrl = item.imageUrl
        name = item.name
        description = item.description
        price = item.price
    }

    Item getItem() {
        def item = id ?
            Item.get(id) :
            new Item(address:new Address(), contactInfo:new SellerContactInfo(), product:product)

        item.address.properties = address.properties
        item.contactInfo.properties = contactInfo.properties
        item.name = name
        item.description = description
        item.imageUrl = imageUrl
        item.price = price

        return item
    }

    def getTagList() {
        tagString ? tagString.split("\\s").toList() : []
    }

    def hasNoErrors() {
        validateCaptcha()
        return !this.errors.hasErrors()
    }

    def validateCaptcha() {
        // TODO captchaService not injected at the time of regular validation (might be fixed in Grails 1.1)
        if (!captchaService.validateCaptchaString(captcha)) {
            this.errors.rejectValue("captcha", "default.captcha.does.not.match")
        }
    }

    def handleFileUpload() {
        if (file && !file.empty) {
            if (imageUrl) {
                // Delete the old image when a new is uploaded
                imageStorageService.deleteImage(imageUrl)
            }
            imageUrl = imageStorageService.storeUploadedImage(file.bytes, file.contentType)
        }
    }
}
