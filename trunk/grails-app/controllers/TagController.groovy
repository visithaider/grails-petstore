class TagController {

    def byTag = {
        if (params.tag) {
            def paginateParams = [offset:(params.offset ?: "0").toInteger(), max:(params.max ?: "10").toInteger(),
                    sort:params.sort,order:params.order]
            def items = Item.findAllByTag(params.tag, paginateParams)
            /*
            def items = Item.createCriteria().list {
                tags {
                    eq "tag", params.tag
                }
                maxResults 3
            }
            */
            def total = Item.countAllByTag(params.tag)
            def headline = "Found ${total} pets tagged as '${params.tag}'"
            render(view:"/item/list", model:[itemList:items, total:total, headline:headline])
        } else {
            redirect(controller:"item",action:"list")
        }

    }

}