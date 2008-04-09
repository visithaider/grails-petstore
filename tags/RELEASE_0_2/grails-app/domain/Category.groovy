class Category {

    String name, description, imageUrl

    static constraints = {
        name(blank:false, unique:true)
        description(nullable:true)
        imageUrl(blank:false)
    }

    static mapping = {
        cache true
    }

    SortedSet products
    static hasMany = [products:Product]

    static searchable = true

}
