package gps

class ItemController {

    def captchaService
    def imageStorageService
    def itemService
    
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
        def total = Item.countByCategory(category)
        def items = Item.findAllByCategory(category, params)
        def headline = "Found ${total} pets in category '${category.name}'"
        render(view: "list", model:[itemList:items, total:total, id:params.id, headline:headline])
    }

    def show = {
        [item:Item.get(params.id)]
    }

    def edit = {
        def item = Item.get(params.id)
        [item:item, command:new ItemCommand(item)]
    }

    def create = {
        def command = new ItemCommand(address: new Address(), contactInfo: new SellerContactInfo())
        render(view: "edit", model: [command:command])
    }

    def save = { ItemCommand command ->
        command.handleFileUpload()
        
        def item = command.item
        if (command.hasNoErrors() && itemService.tagAndSave(item, command.tagList)) {
            flash.message = "Saved item ${item}"
            redirect(action:show,id:item.id)
        } else {
            flash.message = "${command.errors.errorCount} validation errors."
            render(view:"edit", model:[item:command.item,command:command])
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

}

