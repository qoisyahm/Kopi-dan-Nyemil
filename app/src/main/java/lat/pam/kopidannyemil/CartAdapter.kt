package lat.pam.kopidannyemil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

class CartAdapter(
    private val cartItems: MutableList<CartItem>,
    private val onItemQuantityChanged: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCartMenu: ImageView = itemView.findViewById(R.id.imgCartMenu)
        val tvCartMenuName: TextView = itemView.findViewById(R.id.tvCartMenuName)
        val tvCartMenuPrice: TextView = itemView.findViewById(R.id.tvCartMenuPrice)
        val btnCartMinus: TextView = itemView.findViewById(R.id.btnCartMinus)
        val tvCartQuantity: TextView = itemView.findViewById(R.id.tvCartQuantity)
        val btnCartPlus: TextView = itemView.findViewById(R.id.btnCartPlus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.tvCartMenuName.text = cartItem.menu.nama

        val itemTotal = cartItem.menu.getHargaNumeric() * cartItem.quantity
        val formatter = NumberFormat.getNumberInstance(Locale.Builder().setLanguage("id").setRegion("ID").build())
        holder.tvCartMenuPrice.text = "Rp ${formatter.format(itemTotal)}"

        holder.tvCartQuantity.text = cartItem.quantity.toString()
        holder.imgCartMenu.setImageResource(cartItem.menu.gambarResId)

        holder.btnCartPlus.setOnClickListener {
            cartItem.quantity++
            notifyItemChanged(holder.bindingAdapterPosition)
            onItemQuantityChanged()
        }

        holder.btnCartMinus.setOnClickListener {
            if (cartItem.quantity > 1) {
                cartItem.quantity--
                notifyItemChanged(holder.bindingAdapterPosition)
            } else {
                val currentPos = holder.bindingAdapterPosition
                if (currentPos != RecyclerView.NO_POSITION && currentPos < cartItems.size) {
                    cartItems.removeAt(currentPos)
                    notifyItemRemoved(currentPos)
                    notifyItemRangeChanged(currentPos, cartItems.size)
                }
            }
            onItemQuantityChanged()
        }
    }

    override fun getItemCount(): Int = cartItems.size
}