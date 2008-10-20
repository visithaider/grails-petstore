class ShoppingCartTest extends GroovyTestCase {

    void testAddRemoveClearItems() {
        def cart = new ShoppingCart()
        assert cart.empty
        assert cart.itemCount == 0

        cart.addItem 1
        assert cart.itemCount == 1

        cart.addItem 2
        assert cart.itemCount == 2

        cart.addItem 2
        assert cart.itemCount == 3
        assert cart.getItemCount(2) == 2

        cart.removeItem 2
        assert cart.itemCount == 2
        assert cart.getItemCount(2) == 1

        cart.removeItem 1
        assert cart.itemCount == 1
        assert cart.getItemCount(1) == 0
        assert cart.getItemCount(2) == 1

        cart.clearItem 2
        assert cart.itemCount == 0
        assert cart.getItemCount(1) == 0
        assert cart.getItemCount(2) == 0
        assert cart.empty

        cart.removeItem 1
        assert cart.getItemCount(1) == 0
    }

}