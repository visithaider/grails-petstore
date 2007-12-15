class Product {

    String name, description, imageUrl

    static belongsTo = [item:Item, category:Category]

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
        item(nullable:true)
    }

    String toString() {
        "$name: $description [$category]"   
    }

}
