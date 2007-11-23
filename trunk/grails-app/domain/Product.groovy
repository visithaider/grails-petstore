class Product {

    String name, description

    static belongsTo = [item:Item, category:Category]

    static searchable = {
        category(component:true)
    }

    static constraints = {
        name(blank:false)
        description(blank:false, validator: {
            it?.indexOf("<script") < 0 &&
            it?.indexOf("<link") < 0
        })
    }

    String toString() {
        "$name: $description [$category]"   
    }

}
