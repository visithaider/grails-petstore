package org.grails.petstore

class ItemController {

    def geoCoderService
    def captchaService
    def imageStorageService

    def scaffold = Item

    def defaultAction = "list"

    static final String CAPTCHA_ATTR = "captchaString"

    // TODO: captcha to interceptor around edit/update etc

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
        render(feedType:"rss",contentType:"text/xml") {
            title = "Pets"
            link = g.createLink(action:"list")
            description = "Ten pets from the Grails Pet Store"
            Item.list(max:10).each {
                entry(it.name) {
                    link = g.createLink(action:"list",id:item.id)
                }
            }
        }
    }

    /**
     * Indexed search.
     */
    def search = {
        def view = "searchresult"
        def itemList = []
        if (params.q?.trim()) {
            try {
                itemList = Item.searchEvery(params.q, [offset:params.offset, max:params.max])
            } catch (e) {
                log.error e, e
            }
        }
        render(view:view, model:[itemList: itemList])
    }

    def list = {
        def pid = params.product
        def cid = params.category
        if (pid) {
            def product = Product.get(pid)
            if (product) {
                def items = Item.findAllByProduct(product)
                return [itemList:items]
            }
        }
        if (cid) {
            def category = Category.get(cid)
            if (category) {
                def items = Item.createCriteria().list {
                    "in"("product", category.products)    
                }
                return [itemList:items]
            }
        }
        return [itemList:Item.list()]
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
        def item
        if (params.id) {
            item = Item.get(params.id)
        } else {
            item = new Item(address:new Address(),
                            contactInfo:new SellerContactInfo()
            )
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
        if (session[CAPTCHA_ATTR] && (params[CAPTCHA_ATTR]?.trim() != session[CAPTCHA_ATTR])) {
            item.errors.reject("captchaMismatch", "Captcha did not match")
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