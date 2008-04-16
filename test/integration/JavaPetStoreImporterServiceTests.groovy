class JavaPetStoreImporterServiceTests extends GroovyTestCase {

    SearchableService searchableService
    JavaPetStoreImporterService javaPetStoreImporterService

    protected void setUp() {
        searchableService.stopMirroring()
    }

    void testImport() {
        javaPetStoreImporterService.importProductsAndCategories()
        assert Category.count() == 5
        assert Product.count() == 10

        def itemCount = 102
        javaPetStoreImporterService.importItems(itemCount)
        assert Item.count() == itemCount
        assert Address.count() == itemCount
        assert SellerContactInfo.count() == itemCount
    }

    protected void tearDown() {
        searchableService.startMirroring()
    }

}
