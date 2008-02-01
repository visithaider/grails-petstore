package org.grails.petstore

class ItemUnitTests extends GroovyTestCase {

    void testRating() {
        Item item = new Item()

        assert item.totalScore == 0
        assert item.numberOfVotes == 0
        assert item.checkAverageRating() == 0

        item.addRating(10)

        assert item.totalScore == 10
        assert item.numberOfVotes == 1
        assert item.checkAverageRating() == 10

        item.addRating(5)

        assert item.totalScore == 15
        assert item.numberOfVotes == 2
        assert item.checkAverageRating() == 7.5
    }

}