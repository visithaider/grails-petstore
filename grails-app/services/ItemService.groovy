import grails.util.GrailsUtil

class ItemService {

    static transactional = true

    GeoCoderService geoCoderService
    boolean fetchCoordinates = false

    // TODO asynchronous coordinate fetching using JMS

    synchronized boolean tagAndSave(Item item, List<String> withTags) {
        if (fetchCoordinates) {
            fetchCoordinatesFor item, { tag item, withTags }
        } else {
            tag item, withTags
        }
        item.save()
    }

    private void tag(Item item, List<String> tagsList) {
        /* Tag the item using the string list, creating new tags on demand.
           This is why this method needs to be synchronized. */
        // TODO:
        // this could possibly be handled with serialization=read_uncommitted
        // and a findByTag retry on failed save, but delete-orphan of tags would still be
        // a problem.
        def tagSet = []
        tagsList.unique().each {
            def t = Tag.findByTag(it)
            if (!t) {
                t = new Tag(tag: it)
                t.items.add(item) 
                t.save()
            }
            tagSet.add(t)
        }
        item.tags = tagSet as SortedSet
    }

    private void fetchCoordinatesFor(Item item, Closure doWhileWaiting) {
        /* Fetch coordinates from external service in a background thread,
           to avoid blocking for net I/O wait. */
        def geoCodeTask = Thread.start {
            try {
                def gc = geoCoderService.geoCode(item.address.toString())[0]
                item.latitude = gc?.latitude
                item.longitude = gc?.longitude
                if (log.debugEnabled) {
                    log.debug "Retrieved coordinates: Lat:${item.latitude}, Long:${item.longitude}"
                }
            } catch (e) {
                log.error """Failed to get result of geocode task for address ${item.address},
                             skipping coordinates: ${e}""", GrailsUtil.sanitize(e)
            }
        }
        // While we're waiting for an answer to the coordinate query, do some work
        doWhileWaiting()
        // Wait a specified timeout for coordinates 
        geoCodeTask.join 1000
    }

}