class BootStrap {

    def sunPetstoreImporterService
    def imageStorageService

     def init = { servletContext ->
        imageStorageService.clearDirectories()
        imageStorageService.createDirectories()

        try {
            sunPetstoreImporterService.importProductsAndCategories()
            sunPetstoreImporterService.importItems()
        } catch (e) {
            log.error "Could not import Sun Java Pet Store images", e
        }

     }

     def destroy = {
     }
} 
