class Address {

    String street1, street2, city, state, zip

    static constraints = {
        street1(blank:false)
        street2(nullable:true)
        city(blank:false)
        state(blank:false)
        zip(blank:false,matches:"[0-9]{5}")
    }

    static mapping = {
        cache true
    }

    static belongsTo = Item

    static searchable = true

    String toString () {
        [street1, street2, city, state, zip].findAll { it }.join(", ").trim()
    }

}
