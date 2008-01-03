class BootStrap {

    SunPetstoreImporterService sunPetstoreImporterService

     def init = { servletContext ->

        sunPetstoreImporterService.importProductsAndCategories()
        sunPetstoreImporterService.importItems()

        // Create image upload directories
        new File(ImageStorageService.uploadedDir).mkdirs()
        new File(ImageStorageService.thumbnailDir).mkdirs()
     }

     def destroy = {
     }
} 