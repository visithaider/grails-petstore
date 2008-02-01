package org.grails.petstore

class Item {

    Product product
    Address address
    SellerContactInfo contactInfo

    String name, description, imageUrl
    Integer price, totalScore = 0, numberOfVotes = 0

    //static embedded = ["product", "address", "contactInfo"]

    static searchable = true

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
        def unique = new HashSet(tags)
        synchronized (Item) {
            unique.each {
                def tag = Tag.findByTag(it)
                if (!tag) {
                    tag = new Tag(tag: it)
                    tag.save(flush:true)
                }
                addToTags(tag)
            }
        }
    }

    String tagsAsString() {
        tags?.join(" ")
    }

    boolean containsTag(String sxTag) {
        tags.any { it.equals(sxTag) }
    }

}
