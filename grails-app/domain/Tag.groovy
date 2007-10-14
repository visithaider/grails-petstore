class Tag {

    String tag
    Integer refCount = 0

    static hasMany = [items : Item]
    static belongsTo = Item

}
