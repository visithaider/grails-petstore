package gps

class AddressTests extends GroovyTestCase {
  
    void testValidate() {
        def address = new Address()
        assert !address.validate()

        def err = address.errors
        assert err.errorCount == 4

        def fields = err.allErrors.collect { it.field }
        assert fields == ["city","state","street1","zip"]

        address.street1 = "Address 1"
        address.city = "City"
        address.state = "State"
        address.zip = "12345"

        assert address.validate()
    }


    void testToString() {
        def address = new Address()
        assert "" == address.toString()

        address.street1 = "street1"
        assert "street1" == address.toString()

        address.street2 = "street2"
        assert "street1, street2" == address.toString()

        address.city = "city"
        assert "street1, street2, city" == address.toString()

        address.state = "state"
        assert "street1, street2, city, state" == address.toString()

        address.zip = "zip"
        assert "street1, street2, city, state, zip" == address.toString()
    }
    
}
