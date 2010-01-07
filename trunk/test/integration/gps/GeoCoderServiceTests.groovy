package gps

class GeoCoderServiceTests extends GroovyTestCase {

    def gs = new GeoCoderService()

    void testGetCode() {
		def result = gs.geoCode("1600 Pennsylvania Avenue, Washington D.C.")
		assert result.size() == 1

		GeoPoint gp = result[0]

		assert gp.address == "1600 Pennsylvania Ave NW"

        /*
        assert gp.latitude > 30
        assert gp.latitude < 40

        assert gp.longitude > 70
        assert gp.longitude < 80
        */
        
        println "Lat: ${gp.latitude}, Long: ${gp.longitude}"

        assert gp.city == "Washington"
        assert gp.state == "DC"
        assert gp.zip == "20006"
        assert gp.country == "US"

        assert gs.geoCode(null).size() == 0
	}

}
