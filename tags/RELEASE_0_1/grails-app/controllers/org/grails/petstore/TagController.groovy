package org.grails.petstore

class TagController {

    def listTagged = {
        if (params.tag) {
            def tag = Tag.findByTag(params.tag)
            if (tag) {
                return [itemList:tag.items]
            }
        }
        return [itemList:[]]
    }

}