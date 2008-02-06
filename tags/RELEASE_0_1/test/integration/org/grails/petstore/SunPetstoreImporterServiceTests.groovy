package org.grails.petstore

class SunPetstoreImporterServiceTests extends GroovyTestCase {

    def sunPetstoreImporterService 

    void testImport() {
        sunPetstoreImporterService.importProductsAndCategories()
        assert Category.count() == 5
        assert Product.count() == 10

        sunPetstoreImporterService.importItems()
        def itemCount = 102
        assert Item.count() == itemCount
        assert Address.count() == itemCount
        assert SellerContactInfo.count() == itemCount
    }

}