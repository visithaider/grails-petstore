class TagController {

    def byTag = {
        if (params.tag) {
            def total = Item.countByTag(params.tag)
            def items = Item.findAllByTag(params.tag, params)
            def headline = "Found ${total} pets tagged as '${params.tag}'"
            render(view: "/item/list", model: [itemList: items, total: total, headline: headline])
        } else {
            redirect(controller:"item",action:"list")
        }
    }

}