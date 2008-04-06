class CategoryTests extends GroovyTestCase {

    void testValidate() {
        def category = new Category()
        assert !category.validate()

        def err = category.errors
        assert err.errorCount == 2

        def fields = err.allErrors.collect { it.field }
        assert fields == ["imageUrl","name"]
        
        category.name = "Name"
        category.description = "Description"
        category.imageUrl = "Image Url"

        assert category.validate()
    }

}
