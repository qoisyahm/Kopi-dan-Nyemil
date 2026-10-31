package lat.pam.kopidannyemil

object CartManager {
    val cartList = mutableListOf<CartItem>()

    fun addToCart(menu: Menu) {
        val existing = cartList.find { it.menu.nama == menu.nama }
        if (existing != null) {
            existing.quantity++
        } else {
            cartList.add(CartItem(menu, 1))
        }
    }

    fun getTotalCount(): Int = cartList.sumOf { it.quantity }

    fun getTotalPrice(): Int = cartList.sumOf { it.menu.getHargaNumeric() * it.quantity }

    fun clearCart() {
        cartList.clear()
    }
}