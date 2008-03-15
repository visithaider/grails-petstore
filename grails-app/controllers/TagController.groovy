class TagController {

    def listTagged = {
        if (params.tag) {
            return [itemList:Item.findAllTagged(params.tag)]
        } else {
            return [itemList:[]]
        }
    }

}