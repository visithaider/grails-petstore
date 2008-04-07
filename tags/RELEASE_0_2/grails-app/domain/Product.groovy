class Product implements Comparable {

    String name, description, imageUrl

    static constraints = {
        name(blank:false, unique:true)
        description(blank:false, validator: {
            it?.indexOf("<script") < 0 &&
            it?.indexOf("<link") < 0
        })
        imageUrl(blank:false)
    }

    static mapping = {
        cache true
    }

    static belongsTo = [category:Category]

    static searchable = true

    @Override
    int compareTo(other) {
        return name.compareTo(other.name)
    }

}
