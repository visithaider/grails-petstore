class ItemTests extends GroovyTestCase {

    void testTagsAsString() {
        def item = new Item()
        assert item.tagsAsString() == ""

        ["t1", "t2", "t3"].each {
            item.addToTags(new Tag(tag: it))
        }

        assert item.tagsAsString() == "t1 t2 t3" : item.tagsAsString()
    }

    /*
    void testSave() {
        def item = new Item(
            address:new Address(),
            contactInfo:new SellerContactInfo(),
            product:Product.get(1)
        )
        assert !item.save()

        item.name = "An item"
        item.description = "A test item"
        item.price = 500
        item.imageUrl = "test.jpg"
        item.totalScore = 10
        item.numberOfVotes = 20

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
        assert item.contactInfo.id != null
    }
    */

    void testRating() {
        def item = new Item()

        assert item.totalScore == 0
        assert item.numberOfVotes == 0
        assert item.averageRating() == 0

        item.addRating(10)

        assert item.totalScore == 10
        assert item.numberOfVotes == 1
        assert item.averageRating() == 10

        item.addRating(5)

        assert item.totalScore == 15
        assert item.numberOfVotes == 2
        assert item.averageRating() == 7.5
    }

    // TODO

    void testFindAllByCategory() {
        //Item.findAllByCategory(Category.get(1), [:])
    }

    void testCountAllByCategory() {
        //Item.countAllByCategory(Category.get(1))
    }

    void testFindAllByTag() {
        //Item.findAllByTag("tag", [:])
    }
    
    void testCountAllByTag() {
        //Item.countAllByTag("tag")
    }

}
