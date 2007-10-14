class RatingBean {

    String itemId
    int grade = 0

    List<String> getRatingText() {
        ["Hate It", "Below Average", "Average", "Above Average", "Love It"]
    }

    void setGrade(int grade) {
        if(itemId != null && grade >= 1) {
            Item item = Item.load(itemId)
            item.addRating(grade)
            item.store()
        }
    }

}