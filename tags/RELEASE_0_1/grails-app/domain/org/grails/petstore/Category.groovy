package org.grails.petstore

class Category {

    String name, description, imageUrl

    static searchable = false

    static constraints = {
        name(blank:false, unique:true)
        description(nullable:true)
        imageUrl(blank:false)
    }

    static hasMany = [products:Product]

    @Override
    String toString() {
        "$name: $description"
    }
    
}
