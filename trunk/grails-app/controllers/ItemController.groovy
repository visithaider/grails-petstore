class ItemController {

    CaptchaService captchaService
    ImageStorageService imageStorageService
    ItemService itemService
    
    def defaultAction = "list"

    def list = {
        if (!params.max) {
            params.max = 10
        }
        def total = Item.count()
        def items = Item.list(params)
        def headline = "Found ${total} pets in the catalog"
        [itemList:items,total:total, headline:headline]
    }

    def search = {
        def items = [], total = 0
        if (params.q?.trim()) {
            try {
                // TODO: can't sort on tokenized field name
                def result = Item.search(params.q, params)
                total = result.total
                items = result.results
            } catch (e) {
                log.error "Search error: ${e.message}"
            }
        }
        def headline = "Found ${total} pets matching '${params.q}'"
        render(view:"list", model:[itemList: items, total:total, headline:headline])
    }

    def byProduct = {
        if (!params.max) {
            params.max = 10
        }
        def product = Product.get(params.id)
        def total = Item.countByProduct(product)
        def items = Item.findAllByProduct(product, params)
        def headline = "Found ${total} pets in product '${product.name}'"
        render(view:"list", model:[itemList:items, total:total, id:params.id, headline:headline])
    }

    def byCategory = {
        def category = Category.get(params.id)
        def total = Item.countAllByCategory(category)
        def items = Item.findAllByCategory(category, GpsWebUtils.toHqlParams(params))
        def headline = "Found ${total} pets in category '${category.name}'"
        render(view: "list", model: [itemList: items, total: total, id: params.id, headline: headline])
    }

    def show = {
        [item:Item.get(params.id)]
    }

    def edit = {
        captchaService.setCaptchaString()
        [item: Item.get(params.id)]
    }

    def create = {
        captchaService.setCaptchaString()
        render(view: "edit", model: [item: new Item()])
    }

    def save = {
        def item = params.id ? Item.get(params.id) : new Item()
        item.properties = params
        
        handleFileUpload(params.file, item)

        def tagList = tagStringToList(params.tagString)

        // TODO global error for captcha mismatch 
        item.validate()
        if (captchaMatches(params.captcha) && itemService.tagAndSave(item, tagList)) {
            flash.message = "Saved item ${item.id}"
            redirect(action:show,id:item.id)
        } else {
            flash.message = "${item.errors.errorCount} validation errors."
            captchaService.setCaptchaString()
            render(view:"edit", model:[item:item])
        }
    }

    def delete = {
        def item = Item.get(params.id)
        if (item) {
            imageStorageService.deleteImage(item.imageUrl)
            item.delete()
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

    private def captchaMatches(captcha) {
        captcha?.trim() == captchaService.getCaptchaString()
    }

    private def tagStringToList(tagString) {
        tagString ? tagString.split("\\s").toList() : []
    }

    private def handleFileUpload(file, item) {
        if (!file?.empty) {
            if (item.imageUrl) {
                // Delete the old image when a new is uploaded
                imageStorageService.deleteImage(item.imageUrl)
            }
            item.imageUrl = imageStorageService.storeUploadedImage(file.bytes, file.contentType)
        }
    }

}