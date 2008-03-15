import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import static java.util.concurrent.TimeUnit.MILLISECONDS

class ItemService {

    static transactional = true

    GeoCoderService geoCoderService
    ExecutorService executorService = Executors.newFixedThreadPool(2)
    boolean fetchCoordinates = false

    synchronized boolean tagAndSave(Item item, List<String> withTags) {
        if (fetchCoordinates) {
            whileFetchingCoordinatesFor item, tag(item, withTags)
        } else {
            tag item, withTags
        }
        item.save()
    }

    private void tag(item, tagsList) {
        /* Tag the item using the string list, creating new tags on demand.
           This is why this method needs to be synchronized. */
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
    }

    private def whileFetchingCoordinatesFor = { item, block ->

        /* Fetch coordinates from external service in a background thread,
           to avoid blocking for net I/O wait. */
        def geoCodeTask = {
            def points = geoCoderService.geoCode(item.address.toString())
            return points[0]
        } as Callable
        def geCodeTask = executorService.submit(geoCodeTask)

        block()

        /* Retrieve the coordinates from the background thread,
           but don't fail if we didn't recieve anything inside the timeout. */
        try {
            def gc = geCodeTask.get(1000, MILLISECONDS)
            item.latitude = gc?.latitude
            item.longitude = gc?.longitude
        } catch (e) {
            log.error """Failed to get result of geocode task for address ${item.address},
                         skipping coordinates: ${e.message}"""
        }

    }

}