package org.grails.petstore

class ItemController {

    GeoCoderService geoCoderService
    CaptchaService captchaService
    ImageStorageService imageStorageService

    def scaffold = Item

    def defaultAction = "list"

    def beforeInterceptor = {
        setCaptcha()
    }

    def afterInterceptor = { model ->
        model.put("actionName", actionName)        
    }

    static final String CAPTCHA_ATTR = "captchaString"

    def setCaptcha() {
        session[CAPTCHA_ATTR] = captchaService.generateCaptchaString(6)
    }

    def unsetCaptcha() {
        session.removeAttribute(CAPTCHA_ATTR)
    }

    // Converts integer-valued string parametes to actual integers
    private def toIntParams = { Map map ->
        def intParams = [:]
        map.each { e ->
            if (e.value.isInteger()) {
                intParams.put(e.key, e.value.toInteger())
            } else {
                intParams.put(e)
            }
        }
        return intParams
    }

    def rss = {
        render(feedType:"rss",feedVersion:"2.0") {
            title = "Pets"
            link = g.createLink(action:"list")
            description = "Ten pets from the Grails Pet Store"
            Item.list(max:10).each { item ->
                entry(item.name) {
                    link = g.createLink(action:"list",id:item.id)
                    item.description
                }
            }
        }
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
        def product = Product.get(params.product)
        def items = Item.findAllByProduct(product, params)
        def total = Item.countByProduct(product)
        render(view:"list", model:[itemList:items, total:total, id:params.product])
    }

    def byCategory = {
        def category = Category.get(params.category)
        def total = Item.executeQuery(
                "select count(*) from org.grails.petstore.Item i where i.product.category = ?", [category])
        def items = Item.findAll(
                "from org.grails.petstore.Item where product.category = ?",
                [category], toIntParams(params))
        render(view:"list", model:[itemList:items, total:total[0], id:params.category])
    }

    def edit = {
        [item:Item.get(params.id)]
    }

    def create = {
        def item = new Item(params)
        render(view:"edit",model:[item:item])
    }

    def save = {
        def item
        if (params.id) {
            item = Item.get(params.id)
        } else {
            item = new Item(address:new Address(), contactInfo:new SellerContactInfo())
        }

        item.tag(params.tagNames?.split("\\s") as List)

        bindData(item, params)
        bindData(item.address, params, "address")
        bindData(item.contactInfo, params, "contactInfo")

        def uploaded = request.getFile("file")
        if (!uploaded.empty) {
            if (item.imageUrl) {
                imageStorageService.deleteImage(item.imageUrl)
            }
            item.imageUrl = imageStorageService.storeUploadedImage(uploaded.bytes, uploaded.contentType)
        }

        item.validate()
        def captchaMismatch = session[CAPTCHA_ATTR] && (params[CAPTCHA_ATTR]?.trim() != session[CAPTCHA_ATTR])
        if (captchaMismatch) {
            item.errors.reject("captchaMismatch", "Captcha did not match")
        }

        if (!item.errors.hasErrors() && item.save()) {               
            unsetCaptcha()
            flash.message = "Saved item with id = " + item.id
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
            flash.message = "You rated ${item.name} as ${params.rating}"
            redirect(action:show,id:params.id)
        } else {
            redirect(action:list)
        }
    }
}