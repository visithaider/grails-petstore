class BootStrap {

    def sunPetstoreImporterService
    def imageStorageService

     def init = { servletContext ->

        imageStorageService.clearDirectories()
        imageStorageService.createDirectories()

        sunPetstoreImporterService.importProductsAndCategories()
        sunPetstoreImporterService.importItems()
     }

     def destroy = {
     }
} 
