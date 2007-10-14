class BootStrap {

     def init = { servletContext ->
        new Category(name:"Cat",description:"Cat",imageURL:"none").save()
        new Category(name:"Dog",description:"Dog",imageURL:"none").save()
     }
     def destroy = {
     }
} 