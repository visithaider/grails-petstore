import grails.util.GrailsUtil
import org.grails.petstore.SunPetstoreImporterService

class BootStrap {

    SunPetstoreImporterService sunPetstoreImporterService
    boolean importJps = true
    int maxItems = 20

     def init = { servletContext ->
        try {
            sunPetstoreImporterService.importProductsAndCategories()
            if (importJps) {
                Thread.start {
                    log.info "Started import of $maxItems items in background task"
                    sunPetstoreImporterService.importItems(maxItems)
                    log.info "Import completed"
                }
            }
        } catch (e) {
            log.error "Could not import from Java Pet Store", GrailsUtil.sanitize (e)
        }

     }

     def destroy = {}

} 
