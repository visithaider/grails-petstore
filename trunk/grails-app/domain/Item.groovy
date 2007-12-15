class Item {              

    Product product
    Address address
    SellerContactInfo contactInfo

    String name, description, imageUrl
    Integer price, totalScore = 0, numberOfVotes = 0

    // TODO: embedding breaks cascading in 1.0-RC2 and earlier
    //static embedded = ["product", "address", "contactInfo"]

    static searchable = {
        except = ["imageURL", "version"]
        tags(component:true)
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

    Double checkAverageRating(){
        totalScore > 0 ? totalScore/numberOfVotes : 0
    }

    String tagsAsString() {
        tags?.join(" ")
    }

    Boolean containsTag(String sxTag) {
        tags.any { it.equals(sxTag) }
    }

}
