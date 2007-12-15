class SunPetstoreImporterServiceTests extends GroovyTestCase {

    void testImportProductsAndCategories() {
        SunPetstoreImporterService service = new SunPetstoreImporterService()
        service.importProductsAndCategories()
    }

    void testImportItems() {
        SunPetstoreImporterService service = new SunPetstoreImporterService()
        service.importItems()
    }

}