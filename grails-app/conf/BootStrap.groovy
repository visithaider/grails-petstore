import grails.util.GrailsUtil

class BootStrap {

    JavaPetStoreImporterService javaPetStoreImporterService
    SearchableService searchableService
    boolean importJps = true
    int maxItems = (GrailsUtil.environment == "production" ? 102 : 10)

     def init = { servletContext ->
        searchableService.stopMirroring()
        try {
            javaPetStoreImporterService.importProductsAndCategories()
            if (importJps) {
                javaPetStoreImporterService.importItems(maxItems)
                searchableService.indexAll()
            }
        } catch (e) {
            log.error "Could not import from Java Pet Store", GrailsUtil.sanitize (e)
        } finally {
            searchableService.startMirroring()
        }
     }

     def destroy = {}

} 
