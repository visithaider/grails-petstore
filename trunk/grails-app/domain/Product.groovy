class Product {

    Category category
    Item item
    String name, description

    static belongsTo = [Item, Category]

/*
    static searchable = {
        category(component:true)
    }
*/

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
