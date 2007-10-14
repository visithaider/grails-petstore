class Address {

    String street1, street2, city, state, zip
    Double latitude, longitude

    static constraints = {
        street1(blank:false)
        city(blank:false)
        state(blank:false)
        zip(blank:false)
        latitude(nullable:true)
        longitude(nullable:true)
    }

    String toString () {
        "$street1 $street2, $city, $state, $zip"
    }

}
