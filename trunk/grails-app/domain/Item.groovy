class Item {              

    Product product
    Address address
    SellerContactInfo contactInfo

    String imageURL
    Integer price, totalScore = 0, numberOfVotes = 0

    static embedded = ["product", "address", "contactInfo"]

    static searchable = {
        except = ["imageURL", "version"]
        tags(component:true)
    }
    
    static hasMany = [tags : Tag]

    static constraints = {
        price(min:0)
        imageURL(matches:".*[jpeg|jpg|gif|png]\$")
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
        String s = ""
        tags.each { t ->
            s += "${t.tag} "
        }
        s.trim();
    }

    Boolean containsTag(String sxTag) {
        tags.any { it.equals(sxTag) }
    }

}
