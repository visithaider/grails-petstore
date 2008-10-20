class Customer implements Serializable {

    String name
    String email
    Address address
    
    static belongsTo = CustomerOrder
    
}
