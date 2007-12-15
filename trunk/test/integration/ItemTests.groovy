class ItemTests extends GroovyTestCase {

    void testTagsAsString() {
        Item item = new Item()
        item.addToTags(new Tag(tag:"t1"))
        item.addToTags(new Tag(tag:"t2"))
        item.addToTags(new Tag(tag:"t3"))

        assert item.tagsAsString() == "t1 t2 t3"
    }

    void testValidate() {
        Item item = new Item()
        /* TODO: check field errors
        item.product.name = ""
        item.product.description = "De<script>ion"
        item.product.description = "De<link>ion"
        item.description = "Description"
        item.price = -100
        */
        assert !item.validate()
        //println item.errors.allErrors
    }

    void testSave() {
        Item item = new Item(
            address:new Address(),
            product:new Product(),
            contactInfo:new SellerContactInfo()
        )
        assert !item.vaidate()

        item.name = "An item"
        item.description = "A test item"
        item.price = 500
        item.imageUrl = "test.jpg"
        item.totalScore = 10
        item.numberOfVotes = 20

        item.product.name = "Name"
        item.product.description = "Description"
        item.product.imageUrl = "test_product.jpg"
        item.addToTags(new Tag(tag:"foo"))
        item.addToTags(new Tag(tag:"bar"))

        Category category = new Category(name:"Cats",description:"Cats are hairy animals")
        item.product.category = category

        item.address.street1 = "Street1"
        item.address.street2 = "Street2"
        item.address.city = "City"
        item.address.zip = "12345"
        item.address.state = "State"

        item.contactInfo.firstName = "Peter"
        item.contactInfo.lastName = "Backlund"
        item.contactInfo.email = "foo@bar.com"

        assert item.save()


        assert item.id != null
        assert item.address.id != null
        assert item.product.id != null
        assert item.contactInfo.id != null
    }
}
