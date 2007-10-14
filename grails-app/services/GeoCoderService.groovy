import org.apache.log4j.Logger

class GeoCoderService {
	
	boolean transactional = false
    String proxyHost
    int proxyPort = 0
    boolean proxySet = false

    static final String applicationId = "org.grails.petstore.geocoder"
    static final String SERVICE_URL = "http://api.local.yahoo.com/MapsService/V1/geocode"
    static final Logger logger = Logger.getLogger(GeoCoderService)

    void setApplicationId(String applicationId) {
        assert applicationId != null
        this.applicationId = applicationId
    }

    void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost
        this.proxySet = false
    }

    void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort
        this.proxySet = false
    }

    List<GeoPoint> geoCode(String location) {
        if (location == null) {
            return []
        }

        if (!proxySet) {
            setProxyConfiguration()
            proxySet = true
        }

        location = URLEncoder.encode(location, "ISO-8859-1")
        String url = SERVICE_URL + "?appid=" + applicationId + "&location=" + location

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

    private synchronized void setProxyConfiguration() {
        if ((proxyHost == null) || (proxyPort == 0)) {
            return
        }
        try {
            System.setProperty("http.proxyHost", proxyHost)
            System.setProperty("http.proxyPort", "" + proxyPort)
        } catch (SecurityException e) {
            logger.warn("geoCoder.setProxy", e)
        }
    }

}
