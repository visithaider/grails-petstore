class GeoCoderServiceTests extends GroovyTestCase {

    def gs = new GeoCoderService()

    void testGetCode() {
		def result = gs.geoCode("1600 Pennsylvania Avenue, Washington D.C.")
		assert result.size() == 1

		GeoPoint gp = result[0]

		println gp.properties

		assert gp.address == "1600 Pennsylvania Ave NW"
	    assert gp.latitude == 38.89859
        assert gp.longitude == -77.036473
        assert gp.city == "Washington"
        assert gp.state == "DC"
        assert gp.zip == "20006"
        assert gp.country == "US"

        assert gs.geoCode(null).size() == 0
	}

	void testSetApplicationId() {
        try {
            gs.applicationId = null
            fail "Should not accept null id"
        } catch (Throwable t) { /* Expected */ }

        gs.applicationId = "value"
        assert gs.applicationId == "value"
    }
}
