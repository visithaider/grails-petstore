class Item {

    Product product
    Address address
    SellerContactInfo contactInfo

    String imageURL
    Double price
    Integer totalScore = 0, numberOfVotes = 0, disabled = 0

    static hasMany = [tags : Tag]

    // TODO: cascade validation to contactInfo and address, regexp matching does not work
    static constraints = {
        price(min:0.0d)
        imageURL(matches:".*[jpeg|jpg|gif|png]")
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
