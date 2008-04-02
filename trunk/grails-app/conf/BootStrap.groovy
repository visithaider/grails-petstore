class BootStrap {

    SunPetstoreImporterService sunPetstoreImporterService
    SearchableService searchableService
    boolean importJps = true

     def init = { servletContext ->
        searchableService.stopMirroring()
        try {
            sunPetstoreImporterService.importProductsAndCategories()
            if (importJps) {
                int maxItems = (GrailsUtil.environment == "production" ? 102 : 20)
                Thread.start {
                    log.info "Started import of $maxItems items in background task"
                    sunPetstoreImporterService.importItems(maxItems)
                    searchableService.indexAll()
                    log.info "Import completed"
                }
            }
        } catch (e) {
            log.error "Could not import from Java Pet Store", GrailsUtil.sanitize (e)
        } finally {
            searchableService.startMirroring()
        }
     }

     def destroy = {}

} 
