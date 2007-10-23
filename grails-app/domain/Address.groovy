class Address {

    String street1, street2, city, state, zip
    Double latitude, longitude

    static searchable = true

    static constraints = {
        street1(blank:false)
        street2(nullable:true)
        city(blank:false)
        state(blank:false)
        zip(blank:false,matches:"[0-9]{5}")
        latitude(nullable:true)
        longitude(nullable:true)
    }

    static belongsTo = Item

    String toString () {
        "$street1 $street2, $city, $state, $zip"
    }

}
