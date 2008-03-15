class Tag implements Comparable {

    String tag

    static mapping = {
        cache true
    }

    static constraints = {
        tag(blank:false, unique:true)
    }

    static searchable = true

    @Override
    String toString() {
        tag
    }

    @Override
    int compareTo(otherTag) {
        return tag.compareTo(otherTag.tag)
    }
}
