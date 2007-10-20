class Tag implements Comparable {

    String tag

    static hasMany = [items : Item]
    static belongsTo = Item

    String toString() {
        "Tag: " + tag
    }

    int compareTo(otherTag) {
        return tag.compareTo(otherTag.tag)
    }
}
