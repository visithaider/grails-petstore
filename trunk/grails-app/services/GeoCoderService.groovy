class GeoCoderService {
	
	static transactional = false
	
    private final String APPLICATION_ID = "org.grails.petstore"
    private final String SERVICE_URL = "http://api.local.yahoo.com/MapsService/V1/geocode"

    List<GeoPoint> geoCode(String location) {
        if (location == null) {
            return []
        }

        location = URLEncoder.encode(location, "ISO-8859-1")
        def url = "${SERVICE_URL}?appid=${APPLICATION_ID}&location=${location}"

        def doc = new XmlSlurper().parse(new URL(url).openStream())
        assert doc.name() == "ResultSet"

        def geoPoints = []
        doc.Result.each { result ->
            def point = new GeoPoint()
            point.latitude = Double.valueOf(result.Latitude.text())
            point.longitude = Double.valueOf(result.Longitude.text())
            point.address = result.Address
            point.city = result.City
            point.state = result.State
            point.zip = result.Zip
            point.country = result.Country
            geoPoints.add(point)
        }
        return geoPoints
    }

}
