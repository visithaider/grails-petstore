package gps

import javax.jms.Destination
import org.springframework.jms.core.JmsTemplate

class ItemService {

    static transactional = true

    JmsTemplate jmsTemplate
    Destination coordinatesLookupQueue

    boolean fetchCoordinates = false

    synchronized boolean tagAndSave(Item item, List<String> withTags) {
        tag item, withTags
        def saved = item.save() != null
        if (fetchCoordinates) {
            jmsTemplate.convertAndSend coordinatesLookupQueue, item.id.toString()
        }

        return saved
    }

    /* Tag the item using the string list, creating new tags on demand. */
    private void tag(Item item, List<String> tagsList) {
        List<Tag> tagSet = []
        tagsList.unique().each {
            def t = Tag.findByTag(it)
            if (!t) {
                t = new Tag(tag: it)
                t.save()
            }
            tagSet.add(t)
        }
        item.tags = tagSet as SortedSet
    }

}