package org.grails.petstore

class ItemController {

    GeoCoderService geoCoderService
    CaptchaService captchaService
    ImageStorageService imageStorageService
    ItemService itemService

    def scaffold = Item

    def defaultAction = "list"

    def beforeInterceptor = {
        if (isCaptchaProtectedAction()) {
            session[CAPTCHA_ATTR] = captchaService.generateCaptchaString(6)
        }
    }

    public static final String CAPTCHA_ATTR = ItemController.name + ".CAPTCHA_ATTR"

    def captchaMismatch() {
        session[CAPTCHA_ATTR] &&
        (params[CAPTCHA_ATTR]?.trim() != session[CAPTCHA_ATTR])
    }

    def isCaptchaProtectedAction() {
        actionName in ["edit","create"]
    }

    def search = {
        def items = []
        if (params.q?.trim()) {
            try {
                items = Item.searchEvery(params.q, params)
            } catch (e) {
                log.error e, e
            }
        }
        render(view:"searchresult", model:[itemList: items])
    }

    def list = {
        if (!params.max) {
            params.max = 10
        }
        [itemList:Item.list(params),total:Item.count()]
    }

    def byProduct = {
        def product = Product.get(params.id)
        def total = Item.countByProduct(product)
        def items = Item.findAllByProduct(product, params)
        render(view:"list", model:[itemList:items, total:total, id:params.id])
    }

    def byCategory = {
        def category = Category.get(params.id)
        def total = Item.countAllByCategory(category)
        def items = Item.findAllByCategory(category, params)
        render(view:"list", model:[itemList:items, total:total, id:params.id])
    }

    def edit = {
        [item:Item.get(params.id)]
    }

    def create = {
        render(view: "edit", model: [item: new Item()])
    }

    def save = {
        def item
        if (params.id) {
            item = Item.get(params.id)
        } else {
            item = new Item(address:new Address(), contactInfo:new SellerContactInfo())
        }

        bindData(item, params)
        bindData(item.address, params, "address")
        bindData(item.contactInfo, params, "contactInfo")

        def uploaded = request.getFile("file")
        if (!uploaded.empty) {
            if (item.imageUrl) {
                // Delete the old image when a new is uploaded
                imageStorageService.deleteImage(item.imageUrl)
            }
            item.imageUrl = imageStorageService.storeUploadedImage(uploaded.bytes, uploaded.contentType)
        }

        if (captchaMismatch()) {
            item.errors.reject("captchaMismatch", "Captcha did not match")
        }

        def tagList = params.tagNames?.split("\\s").toList()

        item.validate()
        if (!item.errors.hasErrors() && itemService.tagAndSave(item, tagList)) {
            flash.message = "Saved item ${item.id}"
            redirect(action:show,id:item.id)
        } else {
            flash.message = "${item.errors.errorCount} validation errors."
            render(view:"edit", model:[item:item])
        }
    }

    def delete = {
        Item item = Item.get(params.id)
        if (item) {
            // TODO: wrap in transaction?
            item.delete()
            imageStorageService.deleteImage(item.imageUrl)
        }
        flash.message = "Item ${item.id} deleted."
        redirect(action:list)
    }

    def voteFor = {
        if (params.id && params.rating) {
            def item = Item.get(params.id)
            item.addRating(params.rating.toInteger())
            item.save()
            flash.message = "You gave ${item.name} ${params.rating} points."
            redirect(action:show,id:params.id)
        } else {
            redirect(action:list)
        }
    }

}
