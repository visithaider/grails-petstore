class Item {

    Product product
    Address address
    SellerContactInfo contactInfo

    String name, description, imageURL, imageThumbURL
    Double price
    Integer totalScore = 0, numberOfVotes = 0, disabled = 0

    static hasMany = [tags : Tag]

    // TODO: cascade validation to contactInfo and address
    static constraints = {
        name(blank:false)
        description(blank:false, validator: {
            it.indexOf("<script") < 0 &&
            it.indexOf("<link") < 0
        })
        price(min:0.0d)
        //imageURL(matches:"[jpg|gif|png]\$")
        //imageThumbURL(matches:"[jpg|gif|png]\$")
        product(nullable:true)
    }

    /* Business Methods */

    void addRating(int score){
        totalScore += score
        numberOfVotes += 1
    }

    int checkAverageRating(){
        totalScore > 0 ? totalScore/numberOfVotes : 0
    }

    String tagsAsString() {
        String s = ""
        tags.each { t ->
            s += "${t.tag} "
        }
        s.trim();
    }

    Boolean containsTag(String sxTag) {
        tags.any { it.equals(xTag) }
    }

}
