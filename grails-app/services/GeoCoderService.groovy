class GeoCoderService {
	
	static transactional = false
	
    String proxyHost
    int proxyPort = 0
    boolean proxySet = true

    private static final String APPLICATION_ID = "org.grails.petstore.geocoder"
    private static final String SERVICE_URL = "http://api.local.yahoo.com/MapsService/V1/geocode"

    void setProxy(String host, int port) {
        this.proxyHost = host
        this.proxyPort = port
        proxySet = false
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
        String url = SERVICE_URL + "?appid=" + APPLICATION_ID + "&location=" + location

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
            log.warn "Proxy configuration $proxyHost:$proxyPort is not valid"
            return
        }
        try {
            System.setProperty("http.proxyHost", proxyHost)
            System.setProperty("http.proxyPort", "" + proxyPort)
        } catch (SecurityException e) {
            log.warn("geoCoder.setProxy", e)
        }
    }

}
