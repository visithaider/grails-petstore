import grails.util.GrailsUtil
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import static java.util.concurrent.TimeUnit.MILLISECONDS

class ItemService {

    static transactional = true

    GeoCoderService geoCoderService
    ExecutorService executorService = Executors.newFixedThreadPool(1)
    boolean fetchCoordinates = false

    synchronized boolean tagAndSave(Item item, List<String> tagList) {
        if (fetchCoordinates) {
            fetchCoordinates item, tagList
        } else {
            tag item, tagList
        }
        item.save()
    }

    def tag = { item, tagsList ->
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
                t.save()
            }
            tagSet.add(t)
        }
        item.tags = tagSet as SortedSet
    }

    private void fetchCoordinates(Item item, List<String> tagList) {
        /* Fetch coordinates from external service in a background thread,
           to avoid blocking for net I/O wait. */
        def geoCodeTask = {
            geoCoderService.geoCode(item.address.toString())[0]
        } as Callable
        def geCodeTask = executorService.submit(geoCodeTask)

        tag(item, tagList)

        /* Retrieve the coordinates from the background thread,
           but don't fail if we didn't recieve anything inside the timeout. */
        try {
            def gc = geCodeTask.get(1000, MILLISECONDS)
            item.latitude = gc?.latitude
            item.longitude = gc?.longitude
            if (log.debugEnabled) {
                log.debug "Retrieved coordinates ${item.latitude}/${item.longitude}"
            }
        } catch (e) {
            log.error """Failed to get result of geocode task for address ${item.address},
                         skipping coordinates: ${e.message}""", GrailsUtil.sanitize(e)
        }

    }

}