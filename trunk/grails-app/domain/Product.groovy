class Product implements Comparable, Serializable {

    String name, description

    static constraints = {
        name(blank:false, unique:true)
        description(blank:false, validator: {
            it?.indexOf("<script") < 0 &&
            it?.indexOf("<link") < 0
        })
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
