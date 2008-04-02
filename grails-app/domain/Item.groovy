class Item {

    Product product
    Address address
    SellerContactInfo contactInfo

    String name, description, imageUrl
    Integer price, totalScore = 0, numberOfVotes = 0
    Date dateCreated, lastUpdated
    Double latitude, longitude

    static constraints = {
        name(blank:false, maxSize:128)
        description(maxSize:2000)
        price(min:0)
        latitude(nullable:true)
        longitude(nullable:true)
    }

    static mapping = {
        cache true
        tags cache:true
    }

    SortedSet tags
    static hasMany = [tags : Tag]

    static searchable = true

    static List findAllByTag(String tag, Map params) {
        Item.executeQuery(
            "select i from Tag t, Item i where t.tag = :tag and t in elements(i.tags)",
            [tag:tag], params)
    }

    static int countAllByTag(String tag) {
        Item.createCriteria().get {
            tags {
                eq "tag", tag
            }
            projections {
                count "id"
            }
        }
    }

    static List findAllByCategory(Category category, Map params) {
        Item.createCriteria().list {
            product {
                eq "category",category
            }
            order(params.sort ?: "name")
            maxResults(params.max?.toInteger() ?: 10)
            firstResult(params.offset?.toInteger() ?: 0)
        }
    }

    static int countAllByCategory(Category category) {
        Item.createCriteria().get {
            product {
                eq "category",category
            }
            projections {
                count "id"
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
        tags ? tags.collect {it.tag}.sort().join(" ") : ""
    }

}
