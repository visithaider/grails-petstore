import javax.jms.MessageListener
import javax.jms.Message

class CoordinatesLookupService implements MessageListener {

    boolean transactional = true

    GeoCoderService geoCoderService

    void onMessage(Message message) {
        def id = message.text.toLong()

        def item = Item.get(id)
        if (item) {
            try {
                def gc = geoCoderService.geoCode(item.address.toString())[0]
                item.latitude = gc?.latitude
                item.longitude = gc?.longitude
                item.save()
                log.debug "Retrieved coordinates: Latitude:${item.latitude}, Longitude:${item.longitude}"
            } catch (e) {
                log.error """Failed to get result of geocode task for address ${item.address},
                             skipping coordinates: ${e}""", GrailsUtil.sanitize(e)
            }
        } else {
            log.error "No item with id ${id} found"
        }
    }
}
