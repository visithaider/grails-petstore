class Category implements Serializable {

    String name, description

    static constraints = {
        name(blank:false, unique:true)
        description(nullable:true)
    }

    static mapping = {
        cache true
        products cache:true
    }

    SortedSet products
    static hasMany = [products:Product]

    static searchable = true

}
