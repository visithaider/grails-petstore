import org.hibernate.SessionFactory
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests

class ItemTests extends AbstractTransactionalDataSourceSpringContextTests {

    SunPetstoreImporterService sunPetstoreImporterService
    SearchableService searchableService
    SessionFactory sessionFactory

    protected void onSetUpInTransaction() {
        searchableService.stopMirroring()
        sunPetstoreImporterService.importProductsAndCategories()
    }

    void testTagsAsString() {
        Item item = new Item()
        assert item.tagsAsString() == ""

        ["t1", "t2", "t3"].each {
            item.addToTags(new Tag(tag: it))
        }

        assert item.tagsAsString() == "t1 t2 t3" : item.tagsAsString()
    }

    void testSave() {
        Item item = new Item(
            address:new Address(),
            contactInfo:new SellerContactInfo(),
            product:Product.list().get(0)
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

        assert item.save() : item.errors.allErrors.collect {
            "Rejected value '${it.rejectedValue}' on field '${it.field}'\n"
        }

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

    private Item buildItemFrom(String s, Product p) {
        assert p
        def item = new Item(
            address:new Address(),
            contactInfo:new SellerContactInfo(),
            product:p
        )
        item.name = "Name ${s}"
        item.description = "Description ${s}"
        item.imageUrl = "Image url ${s}"
        item.address.street1 = "Address street 1 ${s}"
        item.address.city = "Address city ${s}"
        item.address.state = "Address state ${s}"
        item.contactInfo.firstName = "Contact info first name ${s}"
        item.contactInfo.lastName = "Contact info last name ${s}"
        
        item.contactInfo.email = "${s}@${s}.com"
        item.address.zip = "12345"
        item.price = 100

        if (!item.validate()) {
            item.errors.allErrors.each {
                println it
            }
        }

        item
    }

    void testFindAllByCategory() {
        def p1 = Product.list().get(0)
        def p2 = Product.list().get(1)

        def i1 = buildItemFrom("A", p1)
        def i2 = buildItemFrom("B", p1)
        def i3 = buildItemFrom("C", p2)

        assert i1.save() && i2.save() && i3.save()

        def params = [:]

        def itemsByCategory = Item.findAllByCategory(p1.category, params)
        assert itemsByCategory == [i1, i2]

        itemsByCategory = Item.findAllByCategory(p2.category, params)
        assert itemsByCategory == [i3]
    }

}
