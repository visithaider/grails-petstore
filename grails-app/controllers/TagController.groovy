class TagController {

    def byTag = {
        if (params.tag) {
            def total = Item.countAllByTag(params.tag)
            def items = Item.findAllByTag(params.tag, GpsWebUtils.toHqlParams(params))
            def headline = "Found ${total} pets tagged as '${params.tag}'"
            render(view: "/item/list", model: [itemList: items, total: total, headline: headline])
        } else {
            redirect(controller:"item",action:"list")
        }

    }

}