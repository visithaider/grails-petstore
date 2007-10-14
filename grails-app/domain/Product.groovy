class Product {

    Category category
    String name, description

    static constraints = {
        name(blank:false)
        description(blank:false, validator: {
            it.indexOf("<script") < 0 &&
            it.indexOf("<link") < 0
        })
    }

}
