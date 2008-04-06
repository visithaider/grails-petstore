import grails.util.GrailsUtil

class BootStrap {

    SunPetstoreImporterService sunPetstoreImporterService
    SearchableService searchableService
    boolean importJps = true
    int maxItems = (GrailsUtil.environment == "production" ? 102 : 10)

     def init = { servletContext ->
        searchableService.stopMirroring()
        try {
            sunPetstoreImporterService.importProductsAndCategories()
            if (importJps) {
                log.info "Started import of $maxItems items"
                sunPetstoreImporterService.importItems(maxItems)
                searchableService.indexAll()
                log.info "Import completed"
            }
        } catch (e) {
            log.error "Could not import from Java Pet Store", GrailsUtil.sanitize (e)
        } finally {
            searchableService.startMirroring()
        }
     }

     def destroy = {}

} 
