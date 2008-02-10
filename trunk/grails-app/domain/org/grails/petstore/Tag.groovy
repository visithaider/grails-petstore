package org.grails.petstore

class Tag implements Comparable {

    String tag

    static searchable = false

    static belongsTo = Item

    String toString() {
        tag
    }

    int compareTo(otherTag) {
        return tag.compareTo(otherTag.tag)
    }
}
