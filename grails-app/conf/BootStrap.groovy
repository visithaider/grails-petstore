class BootStrap {

    SunPetstoreImporterService importerService

     def init = { servletContext ->

        importerService.importProductsAndCategories()

        // Create image upload directories
        new File(ImageStorageService.uploadedDir).mkdirs()
        new File(ImageStorageService.thumbnailDir).mkdirs()
     }

     def destroy = {
     }
} 