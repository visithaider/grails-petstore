class SellerContactInfo {

    String firstName, lastName, email

    static constraints = {
        firstName(blank:false)
        lastName(blank:false)
        email(blank:false,email:true)
    }

    static mapping = {
        cache true
    }

    static belongsTo = Item

    static searchable = true

    String toString() {
        "$firstName $lastName <$email>"
    }

}
