package org.grails.petstore

class Product {

    String name, description, imageUrl

    static belongsTo = [category:Category]

    static searchable = false

    static constraints = {
        name(blank:false, unique:true)
        description(blank:false, validator: {
            it?.indexOf("<script") < 0 &&
            it?.indexOf("<link") < 0
        })
        imageUrl(blank:false)
    }

    String toString() {
        "$name: $description [$category]"   
    }

}