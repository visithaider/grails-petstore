class SellerContactInfoTests extends GroovyTestCase {

    void testValidate() {
        def ci = new SellerContactInfo()
        assert !ci.validate()

        // TODO
    }

    void testToString() {
        def ci = new SellerContactInfo()
        ci.firstName = "Foo"
        ci.lastName = "Bar"
        ci.email = "foo@bar"

        assert ci.toString() == "Foo Bar <foo@bar>"
    }

}
