class SellerContactInfo {

    String firstName, lastName, email

    static searchable = true

    static constraints = {
        firstName(blank:false)
        lastName(blank:false)
        email(blank:false,email:true)
    }

    String toString() {
        "$firstName $lastName <$email>"
    }

}