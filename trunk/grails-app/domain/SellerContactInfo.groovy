class SellerContactInfo {

    String firstName, lastName, email

    static constraints = {
        firstName(blank:false)
        lastName(blank:false)
        email(email:true)
    }

}
