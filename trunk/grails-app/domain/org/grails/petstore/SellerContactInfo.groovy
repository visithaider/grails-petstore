package org.grails.petstore

class SellerContactInfo {

    String firstName, lastName, email

    //static belongsTo = [Item]

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
