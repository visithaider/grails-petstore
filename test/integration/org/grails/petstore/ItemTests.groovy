package org.grails.petstore

class ItemTests extends GroovyTestCase {

    SunPetstoreImporterService sunPetstoreImporterService

    protected void setUp() {
        sunPetstoreImporterService.importProductsAndCategories()
    }

    void testTagsAsString() {
        Item item = new Item()
        assert item.tagsAsString() == ""

        ["t1", "t2", "t3"].each {
            item.addToTags(new Tag(tag: it))
        }

        assert item.tagsAsString() == "t1 t2 t3"
    }

    void testContainsTag() {
        Item item = new Item()
        assert !item.containsTag("t1")

        def tags = ["t1", "t2", "t3"]
        tags.each {
            item.addToTags(new Tag(tag:it))
        }

        tags.each {
            assert item.containsTag(it)
        }
    }

    void testValidate() {
        Item item = new Item()
        assert !item.validate()
    }

    void testSave() {
        Item item = new Item(
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

    void testRating() {
        Item item = new Item()

        assert item.totalScore == 0
        assert item.numberOfVotes == 0
        assert item.checkAverageRating() == 0

        item.addRating(10)

        assert item.totalScore == 10
        assert item.numberOfVotes == 1
        assert item.checkAverageRating() == 10

        item.addRating(5)

        assert item.totalScore == 15
        assert item.numberOfVotes == 2
        assert item.checkAverageRating() == 7.5
    }

    Item buildItemFrom(String s, Product p) {
        def item = new Item(
            address:new Address(),
            contactInfo:new SellerContactInfo(),
            product:p
        )
        item.name =
        item.description =
        item.imageUrl =
        item.address.street1 =
        item.address.zip =
        item.address.city =
        item.address.state =
        item.contactInfo.firstName =
        item.contactInfo.lastName =
        item.contactInfo.email = s

        item
    }

    void testFindAllByCategory() {
        def p1 = Product.get(1)
        def p2 = Product.get(2)
        def i1 = buildItemFrom("A", p1)
        def i2 = buildItemFrom("B", p1)
        def i3 = buildItemFrom("C", p2)

        assert i1.save() && i2.save() && i3.save()

        def params = [:]

        def list1 = Item.findAllByCategory(p1.category, params)
        assert list1 == [i1, i2]

        def list2 = Item.findAllByCategory(p2.category, params)
        assert list2 == [i3]


    }

}
