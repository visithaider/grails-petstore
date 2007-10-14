class BootStrap {

     def init = { servletContext ->
        def categoryNames = [
            "Hairy Cat","Groomed Cat", "Medium Dogs", "Small Dogs", "Parrot",
            "Exotic","Small Fish", "Large Fish", "Slithering Reptiles", "Crawling Reptiles"]
        categoryNames.each {
            new Category(name:it,description:it,imageURL:"").save()
        }
     }
     def destroy = {
     }
} 