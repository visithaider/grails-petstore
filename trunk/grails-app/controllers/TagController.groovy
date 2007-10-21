class TagController {

    def index = {
        render {
            h1 "Tags"
            Tag.findAll().each { t ->
                p {
                    a(href:"show/${t.tag}", t.tag + " : " + t.items.size() + " item(s)")
                }
            }
        }
    }

    def show = {
        render {
            h1 "Items with tag ${params.id}"
            Tag.findByTag(params.id)?.items.each {
                p "${it.product.name} - ${it.product.description} (${it.product.category.name})"
            }
        }
    }

}