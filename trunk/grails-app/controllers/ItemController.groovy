import org.springframework.web.multipart.MultipartFile

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
        def item = Item.get(params.id)
        [item:item,command:new ItemCommand(item)]
    }

    def create = {
        captchaService.setCaptchaString()
        def item = new Item()
        render(view: "edit", model: [item:item, command: new ItemCommand(item)])
    }

    def save = { ItemCommand command ->
        def item = params.id ?
            Item.get(params.id) :
            new Item(address:new Address(), contactInfo:new SellerContactInfo())

        bindData(item, params, ["tags"])
        bindData(item.address, params, "address")
        bindData(item.contactInfo, params, "contactInfo")
        handleFileUpload(command, item)

        item.validate()
        if (!(command.errors.hasErrors() || item.errors.hasErrors()) &&
            itemService.tagAndSave(item, command.tagList)) {

            flash.message = "Saved item ${item.id}"
            redirect(action:show,id:item.id)
        } else {
            flash.message = "${item.errors.errorCount + command.errors.errorCount} validation errors."
            captchaService.setCaptchaString()
            render(view:"edit", model:[item:item, command:command])
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

    private void handleFileUpload(ItemCommand cmd, Item item) {
        if (!cmd.file.empty) {
            if (item.imageUrl) {
                // Delete the old image when a new is uploaded
                imageStorageService.deleteImage(item.imageUrl)
            }
            item.imageUrl = imageStorageService.storeUploadedImage(cmd.file.bytes, cmd.file.contentType)
        }
    }

}

// TODO: captchaService not injected properly
// TODO: try to fit the Item in here too
class ItemCommand {
    MultipartFile file
    String tags, captcha

    CaptchaService captchaService

    static constraints = {
        captcha(blank:false, validator: {
            //it?.trim() == captchaService.getCaptchaString()
        })
    }

    ItemCommand(Item item) {
        tags = item ? item.tagsAsString() : ""
        //captchaService.setCaptchaString()
    }

    List<String> getTagList() {
        tags ? tags.split("\\s").toList() : []
    }

}