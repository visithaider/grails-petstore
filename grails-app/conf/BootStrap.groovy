import grails.util.GrailsUtil

class BootStrap {

    JavaPetStoreImporterService javaPetStoreImporterService
    SearchableService searchableService

     def init = { servletContext ->
        searchableService.stopMirroring()
        try {
            javaPetStoreImporterService.importProductsAndCategories()
            javaPetStoreImporterService.importItems()
            searchableService.indexAll()
        } catch (e) {
            log.error "Could not import from Java Pet Store", GrailsUtil.sanitize (e)
        } finally {
            searchableService.startMirroring()
        }
     }

     def destroy = {}

} 
