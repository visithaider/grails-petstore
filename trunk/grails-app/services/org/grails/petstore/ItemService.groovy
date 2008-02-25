package org.grails.petstore

import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class ItemService {

    def executorService = Executors.newFixedThreadPool(2)
    GeoCoderService geoCoderService

    static transactional = true

    synchronized boolean tagAndSave(Item item, List<String> tagsList) {
        def geoCodeTask = {
            def points = geoCoderService.geoCode(item.address.toString())
            return points[0]
        } as Callable
        def geCodeTask = executorService.submit(geoCodeTask)

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

        try {
            def gc = geCodeTask.get(1000, TimeUnit.MILLISECONDS)
            item?.latitude = gc.latitude
            item?.longitude = gc.longitude
        } catch (e) {
            log.error """
                Failed to get result of geocode task for address ${item.address},
                skipping coordinates: ${e.message}
            """
        }

        return item.save()
    }

}