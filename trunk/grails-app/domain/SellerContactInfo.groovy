class SellerContactInfo {

    String firstName, lastName, email

    static belongsTo = Item

    static mapping = {
        cache true
    }

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
