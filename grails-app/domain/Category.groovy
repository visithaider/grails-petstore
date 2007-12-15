class Category {

    String name, description, imageUrl

    static searchable = true

    static constraints = {
        name(blank:false, unique:true)
        description(nullable:true)
        imageUrl(blank:false)
    }

    static hasMany = [products:Product]

    static belongsTo = Product

    @Override
    String toString() {
        "$name: $description"
    }
    
}
