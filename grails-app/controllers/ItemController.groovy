class ItemController {

    CaptchaService captchaService
    ImageStorageService imageStorageService
    ItemService itemService

    def defaultAction = "list"

    def beforeInterceptor = {
        if (isCaptchaProtectedAction()) {
            captchaService.setCaptchaString()
        }
    }

    def captchaMismatch() {
        params["captcha"]?.trim() != captchaService.getCaptchaString()
    }

    def isCaptchaProtectedAction() {
        actionName in ["edit","create"]
    }

    def search = {
        def items = [], total = 0
        if (params.q?.trim()) {
            try {
                def result = Item.search(params.q, params)
                total = result.total
                items = result.results
            } catch (e) {
                log.error "Search error: ${e.message}"
            }
        }
        render(view:"searchresult", model:[itemList: items, total:total])
    }

    def show = {
        def item = Item.get(params.id)
        [item:item]
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
        def item = Item.get(params.id)
        [item:item, tags:item.tagsAsString()]
    }

    def create = {
        render(view: "edit", model: [item: new Item()])
    }

    private void handleFileUpload(Item item) {
        def uploaded = request.getFile("file")
        if (!uploaded.empty) {
            if (item.imageUrl) {
                // Delete the old image when a new is uploaded
                imageStorageService.deleteImage(item.imageUrl)
            }
            item.imageUrl = imageStorageService.storeUploadedImage(uploaded.bytes, uploaded.contentType)
        }
    }

    def save = {
        def item
        if (params.id) {
            item = Item.get(params.id)
        } else {
            item = new Item(address:new Address(), contactInfo:new SellerContactInfo())
        }

        bindData(item, params, ["tags"])
        bindData(item.address, params, "address")
        bindData(item.contactInfo, params, "contactInfo")

        handleFileUpload(item)

        item.validate()
        if (captchaMismatch()) {
            item.errors.reject("captchaMismatch", "Captcha did not match")
        }

        def tagList = params.tags?.split("\\s").toList()

        if (!item.errors.hasErrors() && itemService.tagAndSave(item, tagList)) {
            flash.message = "Saved item ${item.id}"
            redirect(action:show,id:item.id)
        } else {
            flash.message = "${item.errors.errorCount} validation errors."
            captchaService.setCaptchaString()
            render(view:"edit", model:[item:item,tags:params.tags])
        }
    }

    def delete = {
        Item item = Item.get(params.id)
        if (item) {
            // TODO: wrap in transaction?
            item.delete()
            imageStorageService.deleteImage(item.imageUrl)
            flash.message = "Item ${item.id} deleted."
        }
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