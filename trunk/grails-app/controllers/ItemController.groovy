import org.compass.core.engine.SearchEngineQueryParseException

class ItemController {

    GeoCoderService geoCoderService
    CaptchaService captchaService
    ImageStorageService imageStorageService
    SearchableService searchableService

    static final String CAPTCHA_ATTR = "captchaString"

    def scaffold = Item
    def defaultAction = "list"

    private void setCaptcha() {
        session[CAPTCHA_ATTR] = captchaService.generateCaptchaString(6)
    }

    private void unsetCaptcha() {
        session.removeAttribute(CAPTCHA_ATTR)
    }

    /**
     * RSS feed.
     */
    def feed = {
        def path = servletContext.contextPath + controllerUri + "/"
        render(feedType:"rss", feedVersion:"2.0") {
            title = "Latest Pets"
            link = path + actionName
            description = "The ten latest pets from the Grails Pet Store"
            Item.list().each { item ->
                entry(item.product.name) {
                    link = path + "show/" + item.id
                    // TODO: content
                }
            }
        }
    }

    /**
     * Indexed search.
     */
    def search = {
        if (!params.q?.trim()) {
            render (model:[:], view:"searchresult")
        }
        try {
            render (
                model:[searchResult: searchableService.search(params.q, params)],
                view:"searchresult"
            )
        } catch (SearchEngineQueryParseException ex) {
            return [parseException: true]
        }
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
            item = new Item(
                    address:new Address(),
                    contactInfo:new SellerContactInfo(),
                    product:new Product()
            )
        }

        if (params.price) {
            item.price = params.price.toInteger()
        }
        if (params.imageURL) {
            item.imageURL = params.imageURL
        }

        bindData(item.address, params, "address")
        bindData(item.product, params, "product")
        bindData(item.contactInfo, params, "contactInfo")

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
            if (item.imageURL) {
                imageStorageService.deleteImage(item.imageURL)
            }
            item.imageURL = imageStorageService.storeUploadedImage(uploaded)
        }

        item.validate()
        if (session[CAPTCHA_ATTR] && (params[CAPTCHA_ATTR]?.trim() != session[CAPTCHA_ATTR])) {
            item.errors.reject("captchaMismatch")
        }

        if (!item.errors.hasErrors() && item.save()) {
            unsetCaptcha()
            flash.message = "Saved item with id = " + item.id
            redirect(action:show,id:item.id)
        } else {
            setCaptcha()
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