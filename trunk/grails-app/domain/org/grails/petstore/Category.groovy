package org.grails.petstore

class Category {

    String name, description, imageUrl

    static searchable = {
        products(component:true)
    }

    static constraints = {
        name(blank:false, unique:true)
        description(nullable:true)
        imageUrl(blank:false)
    }

    SortedSet products
    static hasMany = [products:Product]

    @Override
    String toString() {
        name
    }
    
}
