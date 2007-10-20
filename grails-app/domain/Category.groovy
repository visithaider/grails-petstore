class Category {

    String name, description, imageURL

    static constraints = {
        name(blank:false)
        description(nullable:true)
        imageURL(nullable:true)
    }

    static belongsTo = Product

    @Override
    String toString() {
        "$name: $description"
    }
    
}
