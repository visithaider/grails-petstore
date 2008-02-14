import org.grails.petstore.SunPetstoreImporterService
import org.grails.petstore.ImageStorageService

class BootStrap {

    SunPetstoreImporterService sunPetstoreImporterService
    ImageStorageService imageStorageService
    def importJps = true
    def maxItems = 20

     def init = { servletContext ->
        imageStorageService.clearDirectories()
        imageStorageService.createDirectories()

        try {
            if (importJps) {
                sunPetstoreImporterService.importProductsAndCategories()
                sunPetstoreImporterService.importItems(maxItems)
            }
        } catch (e) {
            log.error "Could not import from Java Pet Store", e
        }

     }

     def destroy = {
     }
} 
