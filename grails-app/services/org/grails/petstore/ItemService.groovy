package org.grails.petstore

class ItemService {

    static transactional = true

    synchronized boolean tagAndSave(Item item, List<String> tagsList) {
        def tagSet = []
        tagsList.unique().each {
            def tag = Tag.findByTag(it)
            if (!tag) {
                tag = new Tag(tag: it)
                tag.save()
            }
            tagSet.add(tag)
        }
        item.tags = tagSet as SortedSet

        item.save()
    }

}