package org.grails.petstore

class Product implements Comparable {

    String name, description, imageUrl

    static belongsTo = [category:Category]

    static mapping = {
        cache true
    }

    static searchable = {
        category(component:true)
    }

    static constraints = {
        name(blank:false, unique:true)
        description(blank:false, validator: {
            it?.indexOf("<script") < 0 &&
            it?.indexOf("<link") < 0
        })
        imageUrl(blank:false)
    }

    @Override
    String toString() {
        name
    }

    @Override
    public int compareTo(otherProduct) {
        return name.compareTo(otherProduct.name)
    }

}
