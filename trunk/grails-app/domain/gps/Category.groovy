package gps

class Category implements Serializable {

    String name, description

    static constraints = {
        name(blank:false, unique:true)
        description(nullable:true)
    }

    static mapping = {
        cache usage:"transactional"
        products cache:"transactional" //, sort:name   // Sorting will work in 1.1
    }

    static hasMany = [products:Product]

    static searchable = true

}
