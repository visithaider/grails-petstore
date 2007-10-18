class ItemTests extends GroovyTestCase {

    void testTagsAsString() {
        Item item = new Item()
        item.addToTags(new Tag(tag:"t1"))
        item.addToTags(new Tag(tag:"t2"))

        assert item.tagsAsString() == "t1 t2"                                            
    }

    void testValidate() {
        Item item = new Item()

    /*
        assert !item.validate()
        item.errors.allErrors.each {
            println it
        }

        item.name = "Name"

        item.description = "De<script>ion"
        assert !item.save()

        item.description = "De<link>ion"
        assert !item.save()

        // Valid
        item.description = "Description"

        item.price = -
        assert !item.save()
  */
    }

}
