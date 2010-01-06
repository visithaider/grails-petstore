import grails.util.GrailsUtil

class BootStrap {

    def javaPetStoreImporterService
    def searchableService

     def init = { servletContext ->
        searchableService.stopMirroring()
        try {
            javaPetStoreImporterService.importProductsAndCategories()
            javaPetStoreImporterService.importItems()
            searchableService.index()
        } catch (e) {
            log.error "Could not import from Java Pet Store", GrailsUtil.sanitize (e)
        } finally {
            searchableService.startMirroring()
        }
     }

     def destroy = {}

} 
