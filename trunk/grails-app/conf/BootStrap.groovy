import grails.util.GrailsUtil
import org.codehaus.groovy.grails.commons.ApplicationHolder

class BootStrap {

    def javaPetStoreImporterService
    def searchableService

     def init = { servletContext ->
        searchableService.stopMirroring()
        try {
            javaPetStoreImporterService.importProductsAndCategories()
            javaPetStoreImporterService.importItems()
            searchableService.index()
            log.info "::: Grails Pet Store version ${ApplicationHolder.application.metadata['app.version']} running :::"
        } catch (e) {
            log.error "Could not import from Java Pet Store", GrailsUtil.sanitize (e)
        } finally {
            searchableService.startMirroring()
        }
     }

     def destroy = {}

} 
