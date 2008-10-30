class Item implements Serializable {

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
        cache usage:"transactional"
        tags cache:"transactional", sort: tag
    }

    static hasMany = [tags : Tag]

    static searchable = true

    static List findAllByTag(String tag, Map params) {
        Item.executeQuery(
            """select i from Tag t, Item i
               where t.tag = :tag and t in elements(i.tags)
               order by lower(i.${params.sort ?: "name"}) ${params.order ?: "desc"}""",
            [tag:tag], params)
    }

    static int countAllByTag(String tag) {
        Item.executeQuery(
            """select count(*) from Tag t, Item i
               where t.tag = :tag and t in elements(i.tags)""",
            [tag:tag]).get(0)
    }

    static List findAllByCategory(Category category, params) {
        Item.createCriteria().list {
            product {
                eq("category", category)
            }
            maxResults(params.max?.toInteger() ?: 10)
            firstResult(params.offset?.toInteger() ?: 0)
            order(params.sort ?: "name", params.order ?: "asc")
            cacheable(true)
        }
    }

    static int countAllByCategory(Category category) {
        Item.createCriteria().get {
            product {
                eq("category", category)
            }
            projections {
                count("id")
            }
            cacheable(true)
        }
    }

    void addRating(int score) {
        totalScore += score
        numberOfVotes += 1
    }

    double averageRating() {
        totalScore > 0 ? totalScore/numberOfVotes : 0.0
    }

    String tagsAsString() {
        tags ? tags.collect {it.tag}.sort().join(" ") : ""
    }

}
