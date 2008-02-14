package org.grails.petstore

class ItemService {

    static transactional = true

    def tagAndSave(item, tagsList) {
        // TODO: this is not concurrency safe
        def tagSet = []
        tagsList.unique().each {
            def tag = Tag.findByTag(it)
            if (!tag) {
                tag = new Tag(tag: it)
                tag.save()  // This tag might have been stored in another thread at this point
            }
            tagSet.add(tag)
        }
        item.tags = tagSet as SortedSet

        item.save()
    }

}