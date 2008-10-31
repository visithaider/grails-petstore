import org.springframework.jms.core.JmsTemplate
import javax.jms.Destination

class ItemService {

    static transactional = true

    JmsTemplate jmsTemplate
    Destination coordinatesLookupQueue

    boolean fetchCoordinates = false

    synchronized boolean tagAndSave(Item item, List<String> withTags) {
        tag item, withTags
        def saved = item.save()
        if (fetchCoordinates) {
            jmsTemplate.convertAndSend coordinatesLookupQueue, item.id.toString()
        }
        return saved
    }

    /* Tag the item using the string list, creating new tags on demand.

       This method needs to be synchronized in order to avoid concurrent
       threads creating duplicate tags. */

    private void tag(Item item, List<String> tagsList) {
        def tagSet = []
        tagsList.unique().each {
            def t = Tag.findByTag(it)
            if (!t) {
                t = new Tag(tag: it, items: [])
                t.save()
            }
            tagSet.add(t)
        }
        item.tags = tagSet as SortedSet
    }

}