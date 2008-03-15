class AddressTests extends GroovyTestCase {

  public void testSave() {
      Address address = new Address()

      address.street1 = "Address 1"
      address.city = "City"
      address.state = "State"
      address.zip = "12345"

      assert address.save()
  }

}
