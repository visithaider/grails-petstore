class TagController {

    def scaffold = Tag
    def defaultAction = "list"

    def showByName = {
        def tag = Tag.findByTag(params.id)
        if (tag) {
            render(view:"show", model:[tag:tag])
        } else {
            flash.message = "Tag ${params.id} not found."
            redirect(action:list)
        }
    }

}