package lat.pam.kopidannyemil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(
    private val listMenu: List<Menu>,
    private val onAddToCart: (Menu) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgMenu: ImageView = itemView.findViewById(R.id.imgMenu)
        val tvNamaMenu: TextView = itemView.findViewById(R.id.tvNamaMenu)
        val tvDeskripsi: TextView = itemView.findViewById(R.id.tvDeskripsi)
        val tvHarga: TextView = itemView.findViewById(R.id.tvHarga)
        val btnAddToCart: ImageView = itemView.findViewById(R.id.btnAddToCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menu = listMenu[position]
        holder.tvNamaMenu.text = menu.nama
        holder.tvDeskripsi.text = menu.deskripsi
        holder.tvHarga.text = menu.harga
        holder.imgMenu.setImageResource(menu.gambarResId)

        holder.btnAddToCart.setOnClickListener {
            onAddToCart(menu)
        }
    }

    override fun getItemCount(): Int = listMenu.size
}