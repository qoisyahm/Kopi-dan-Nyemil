package lat.pam.kopidannyemil

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.Locale

class AlamatActivity : AppCompatActivity() {

    private lateinit var etNama: EditText
    private lateinit var etAlamat: EditText
    private lateinit var etPatokan: EditText
    private lateinit var btnOrderKirim: Button
    private lateinit var layoutOrderItemsContainer: LinearLayout
    private lateinit var tvOrderTotalPrice: TextView
    private lateinit var tvOrderEmptyCart: TextView

    private val currencyFormatter = NumberFormat.getNumberInstance(
        Locale.Builder().setLanguage("id").setRegion("ID").build()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alamat)

        val usernameIntent = intent.getStringExtra("USER_NAME") ?: "User"

        etNama = findViewById(R.id.etNamaLengkapOrder)
        etAlamat = findViewById(R.id.etAlamatOrder)
        etPatokan = findViewById(R.id.etPatokanOrder)
        btnOrderKirim = findViewById(R.id.btnOrderKirim)

        layoutOrderItemsContainer = findViewById(R.id.layoutOrderItemsContainer)
        tvOrderTotalPrice = findViewById(R.id.tvOrderTotalPrice)
        tvOrderEmptyCart = findViewById(R.id.tvOrderEmptyCart)

        // 1. Tampilkan Ringkasan Menu & Total Harga dari CartManager
        displayOrderSummary()

        // 2. Load Alamat Tersimpan (Auto-Load & Auto-Save Feature)
        val sharedPref = getSharedPreferences("UserAddressPref", MODE_PRIVATE)
        val savedName = sharedPref.getString("SAVED_NAME", usernameIntent)
        val savedAlamat = sharedPref.getString("SAVED_ALAMAT", "")
        val savedPatokan = sharedPref.getString("SAVED_PATOKAN", "")

        etNama.setText(savedName)
        etAlamat.setText(savedAlamat)
        etPatokan.setText(savedPatokan)

        if (!savedAlamat.isNullOrEmpty()) {
            Toast.makeText(this, "Alamat tersimpan otomatis dimuat!", Toast.LENGTH_SHORT).show()
        }

        // Auto Save Listener saat pengguna mengetik
        val autoSaveWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                saveAddressToPref()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        etNama.addTextChangedListener(autoSaveWatcher)
        etAlamat.addTextChangedListener(autoSaveWatcher)
        etPatokan.addTextChangedListener(autoSaveWatcher)

        // 3. Tombol Order & Kirim
        btnOrderKirim.setOnClickListener {
            if (etAlamat.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Harap isi alamat pengiriman!", Toast.LENGTH_SHORT).show()
            } else if (CartManager.getTotalCount() == 0) {
                Toast.makeText(this, "Keranjangmu masih kosong!", Toast.LENGTH_SHORT).show()
            } else {
                // Save address before proceeding
                saveAddressToPref()

                // Navigasi ke KonfirmasiActivity & Kosongkan Keranjang
                val intent = Intent(this, KonfirmasiActivity::class.java)
                intent.putExtra("USER_NAME", etNama.text.toString().trim())
                CartManager.clearCart()
                startActivity(intent)
            }
        }
    }

    private fun displayOrderSummary() {
        layoutOrderItemsContainer.removeAllViews()
        val cartItems = CartManager.cartList

        if (cartItems.isEmpty()) {
            tvOrderEmptyCart.visibility = View.VISIBLE
            tvOrderTotalPrice.text = "Rp 0"
        } else {
            tvOrderEmptyCart.visibility = View.GONE
            for (cartItem in cartItems) {
                val itemView = layoutInflater.inflate(android.R.layout.simple_list_item_2, null)
                val text1 = itemView.findViewById<TextView>(android.R.id.text1)
                val text2 = itemView.findViewById<TextView>(android.R.id.text2)

                text1.text = "${cartItem.quantity}x ${cartItem.menu.nama}"
                text1.setTextColor(getColor(R.color.dark_espresso))
                text1.textSize = 14f

                val itemTotal = cartItem.menu.getHargaNumeric() * cartItem.quantity
                text2.text = "Rp ${currencyFormatter.format(itemTotal)}"
                text2.setTextColor(getColor(R.color.accent_red))
                text2.textSize = 12f

                layoutOrderItemsContainer.addView(itemView)
            }

            val grandTotal = CartManager.getTotalPrice()
            tvOrderTotalPrice.text = "Rp ${currencyFormatter.format(grandTotal)}"
        }
    }

    private fun saveAddressToPref() {
        val sharedPref = getSharedPreferences("UserAddressPref", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("SAVED_NAME", etNama.text.toString().trim())
        editor.putString("SAVED_ALAMAT", etAlamat.text.toString().trim())
        editor.putString("SAVED_PATOKAN", etPatokan.text.toString().trim())
        editor.apply()
    }
}