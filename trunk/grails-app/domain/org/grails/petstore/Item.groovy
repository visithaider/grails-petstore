package org.grails.petstore

class Item {

    Product product
    Address address
    SellerContactInfo contactInfo

    String name, description, imageUrl
    Integer price, totalScore = 0, numberOfVotes = 0

    static searchable = {
        address(component:true)
        contactInfo(component:true)
    }

    static hasMany = [tags : Tag]

    static constraints = {
        name(blank:false)
        price(min:0)
        imageUrl(nullable:true, matches:".*[jpeg|jpg|gif|png]\$")
    }

    /* Business Methods */

    void addRating(Integer score){
        totalScore += score
        numberOfVotes += 1
    }

    double checkAverageRating(){
        totalScore > 0 ? totalScore/numberOfVotes : 0
    }

    void tag(List<String> tags) {
        // TODO: this is not concurrency-safe.
        // Need a better strategy to handle concurrent tagging of items, maybe in a service.
        def unique = new HashSet(tags)
        unique.each {
            def tag = Tag.findByTag(it)
            if (!tag) {
                tag = new Tag(tag: it)
                tag.save()  // This tag might have been stored in another thread at this point
            }
            addToTags(tag)
        }
    }

    String tagsAsString() {
        tags?.join(" ")
    }

    boolean containsTag(String sxTag) {
        tags.any { it.equals(sxTag) }
    }

}
