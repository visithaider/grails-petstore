class Product {

    Category category
    String name, description

    static searchable = {
        category(component:true)
    }

    static constraints = {
        name(blank:false)
        description(blank:false, validator: {
            it.indexOf("<script") < 0 &&
            it.indexOf("<link") < 0
        })
    }

    //static belongsTo = Item

    String toString() {
        "$name: $description [$category]"   
    }

}
