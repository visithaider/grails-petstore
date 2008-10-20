class ShoppingCartController {

    ShoppingCart shoppingCart

    def add = {
        if (params.id) {
            shoppingCart.addItem(params.id.toLong())
            flash.message = "Added ${params.id} to shopping cart"
        }
        redirect(controller:"item", action:"show", id:params.id)
    }

    def remove = {
        if (params.id) {
            shoppingCart.removeItem(params.id.toLong())
            flash.message = "Removed ${params.id} from shopping cart"
        }
        redirect(controller:"item", action:"show", id:params.id)
    }

    def clear = {
        if (params.id) {
            shoppingCart.clearItem(params.id.toLong())
            flash.message = "Removed all ${params.id} from shopping cart"
        }
        redirect(controller:"item", action:"list")
    }

    def show = {
        render "Contents: ${shoppingCart.contents}"
    }

}
