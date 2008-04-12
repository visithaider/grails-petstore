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
        tags cache:true, lazy:true
        address lazy:true
        contactInfo lazy:true
        product lazy:true
    }

    SortedSet tags
    static hasMany = [tags : Tag]

    static searchable = true

    // TODO: get(0) on count queries is ugly

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

    static List findAllByCategory(Category category, Map params) {
        Item.executeQuery(
            """select i from Item i, Category c
               where c = :c and i.product in elements(c.products)
               order by lower(i.${params.sort ?: "name"}) ${params.order ?: "asc"}""",
            [c:category], params)
    }

    static int countAllByCategory(Category category) {
        Item.executeQuery(
            """select count(*) from Item i, Category c
               where c = :c and i.product in elements(c.products)""",
            [c:category]).get(0)
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
