class AddressUnitTests extends GroovyTestCase {

    void testToString() {
        Address address = new Address()
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