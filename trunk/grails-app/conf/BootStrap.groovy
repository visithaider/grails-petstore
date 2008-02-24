import org.grails.petstore.ImageStorageService
import org.grails.petstore.SunPetstoreImporterService

class BootStrap {

    SunPetstoreImporterService sunPetstoreImporterService
    ImageStorageService imageStorageService
    def importJps = true
    def maxItems = 102

     def init = { servletContext ->
        imageStorageService.clearDirectories()
        imageStorageService.createDirectories()

        try {
            sunPetstoreImporterService.importProductsAndCategories()
            if (importJps) {
                sunPetstoreImporterService.importItems(maxItems)
            }
        } catch (e) {
            log.error "Could not import from Java Pet Store", e
        }

     }

     def destroy = {
     }
} 
