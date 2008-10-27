class Tag implements Comparable, Serializable  {

    String tag

    //static hasMany = [items:Item]
    static belongsTo = Item

    static constraints = {
        tag(blank:false, unique:true)
    }

    static mapping = {
        cache true
    }

    static searchable = true

    @Override
    int compareTo(other) {
        tag.compareTo(other.tag)
    }

    @Override
    String toString() {
        tag
    }
    
}
