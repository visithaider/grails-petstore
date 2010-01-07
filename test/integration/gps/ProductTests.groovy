package gps

class ProductTests extends GroovyTestCase {

    void testValidate() {
        def product = new Product()

        assert !product.validate()
        def err = product.errors
        assert err.errorCount == 4
        def fields = err.allErrors.collect {it.field}
        assert fields == ["category","description","imageUrl","name"]

        product.name = "Name"
        product.imageUrl = "Image Url"
        product.category = new Category()
        product.description = "<script>Description</script>"

        assert !product.validate()
        err = product.errors
        assert err.errorCount == 1
        fields = err.allErrors.collect {it.field}
        assert fields == ["description"]

        product.description = "<link>Description</link>"

        assert !product.validate()
        err = product.errors
        assert err.errorCount == 1
        fields = err.allErrors.collect {it.field}
        assert fields == ["description"]

        product.description = "Description"
        
        assert product.validate()
    }

    void testCompareTo() {
        def p1 = new Product(name:"A")
        def p2 = new Product(name:"A")
        def p3 = new Product(name:"B")

        assert p1.compareTo(p2) == 0
        assert p1.compareTo(p3) == -1
        assert p3.compareTo(p1) == 1
    }
    
}
