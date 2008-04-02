class SunPetstoreImporterServiceTests extends GroovyTestCase {

    SearchableService searchableService
    SunPetstoreImporterService sunPetstoreImporterService

    protected void setUp() {
        searchableService.stopMirroring()
    }

    void testImport() {
        sunPetstoreImporterService.importProductsAndCategories()
        assert Category.count() == 5
        assert Product.count() == 10

        def itemCount = 102
        sunPetstoreImporterService.importItems(itemCount)
        assert Item.count() == itemCount
        assert Address.count() == itemCount
        assert SellerContactInfo.count() == itemCount
    }

    protected void tearDown() {
        searchableService.startMirroring()
    }

}