package org.grails.petstore

class Item {

    Product product
    Address address
    SellerContactInfo contactInfo

    String name, description, imageUrl
    Integer price, totalScore = 0, numberOfVotes = 0
    Date dateCreated, lastUpdated

    static searchable = {
        address(component:true)
        contactInfo(component:true)
        tags(component:true)
        product(component:true)
    }

    SortedSet tags
    static hasMany = [tags : Tag]

    static constraints = {
        name(blank:false)
        price(min:0)
    }

    static List findAllTagged(tag) {
        return Item.executeQuery("""
            select i from org.grails.petstore.Item as i
            inner join i.tags as t
            with t.tag = ?
            """, [tag])
    }

    static List findAllByCategory(Category category, Map params) {
        Item.createCriteria().list {
            product {
                eq("category",category)
            }
            order(params.sort ?: "name")
            maxResults(params.max?.toInteger() ?: 10)
            firstResult(params.offset?.toInteger() ?: 0)
        }
    }

    static int countAllByCategory(Category category) {
        Item.createCriteria().get {
            product {
                eq("category",category)
            }
            projections {
                count("id")
            }
        }
    }

    def addRating(Integer score) {
        totalScore += score
        numberOfVotes += 1
    }

    def checkAverageRating() {
        totalScore > 0 ? totalScore/numberOfVotes : 0.0
    }

    def tagsAsString() {
        tags ? tags.sort().join(" ") : ""
    }

}
