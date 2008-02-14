package org.grails.petstore

class Tag implements Comparable {

    String tag

    static searchable = true

    String toString() {
        tag
    }

    int compareTo(otherTag) {
        return tag.compareTo(otherTag.tag)
    }
}
