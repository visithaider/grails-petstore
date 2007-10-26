class Category {

    String name, description, imageURL

    static searchable = true

    static constraints = {
        name(blank:false)
        description(nullable:true)
        imageURL(nullable:true)
    }

    static hasMany = [products:Product]

    static belongsTo = Product

    @Override
    String toString() {
        "$name: $description"
    }
    
}
