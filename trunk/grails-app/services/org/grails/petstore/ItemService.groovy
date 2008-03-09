package org.grails.petstore

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class ItemService {

    static transactional = true

    GeoCoderService geoCoderService
    ExecutorService executorService = Executors.newFixedThreadPool(2)

    synchronized boolean tagAndSave(Item item, List<String> tagsList) {

        /* Fetch coordinates from external service in a background thread,
           to avoid blocking for net I/O wait. */
        def geoCodeTask = {
            def points = geoCoderService.geoCode(item.address.toString())
            return points[0]
        } as Callable
        def geCodeTask = executorService.submit(geoCodeTask)

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

        /* Retrieve the coordinates from the background thread,
           but don't fail if we didn't recieve anything inside the timeout. */
        try {
            def gc = geCodeTask.get(1, TimeUnit.MILLISECONDS)
            item.latitude = gc?.latitude
            item.longitude = gc?.longitude
        } catch (e) {
            log.error """Failed to get result of geocode task for address ${item.address},
                         skipping coordinates: ${e.message}"""
        }

        return item.save()
    }

}