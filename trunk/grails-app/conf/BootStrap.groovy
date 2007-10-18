class BootStrap {

     def init = { servletContext ->

        // Create standard categories
        def categoryNames = [
            "Hairy Cat","Groomed Cat", "Medium Dogs", "Small Dogs", "Parrot",
            "Exotic","Small Fish", "Large Fish", "Slithering Reptiles", "Crawling Reptiles"]
        categoryNames.each {
            new Category(name:it,description:it,imageURL:"").save()
        }

        // Create image upload directories
        new File(ImageStorageService.uploadedDir).mkdirs()
        new File(ImageStorageService.thumbnailDir).mkdirs()
     }

     def destroy = {
     }
} 