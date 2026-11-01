package lat.pam.kopidannyemil

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.NumberFormat
import java.util.Locale

class HomeActivity : AppCompatActivity() {

    private lateinit var layoutFloatingCart: View
    private lateinit var tvFloatingCartBadge: TextView
    private lateinit var tvFloatingCartItemCount: TextView
    private lateinit var tvFloatingCartTotalPrice: TextView
    private lateinit var btnViewCart: TextView

    private lateinit var rvMenu: RecyclerView
    private lateinit var menuAdapter: MenuAdapter

    private val allMenuList = listOf(
        Menu("Kopi Susu Gula Aren", "Espresso, susu, gula aren", "Rp 22.000", R.drawable.kopi_susu_aren),
        Menu("Donat Cokelat", "Donut empuk, glaze cokelat", "Rp 12.000", R.drawable.donat_coklat),
        Menu("Sourdough Loaf", "Roti fermentasi alami", "Rp 35.000", R.drawable.sourdough_loaf),
        Menu("Cappuccino", "Espresso, foam susu lembut", "Rp 25.000", R.drawable.cappuccino),
        Menu("Matcha Latte", "Matcha Jepang, susu segar", "Rp 27.000", R.drawable.matcha_latte)
    )

    private val currentDisplayedList = mutableListOf<Menu>()
    private var selectedCategory = "Semua"

    private val currencyFormatter = NumberFormat.getNumberInstance(
        Locale.Builder().setLanguage("id").setRegion("ID").build()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Penanganan System Navigation Bar Window Insets secara akurat
        val bottomNavLayout = findViewById<View>(R.id.bottomNavLayout)
        ViewCompat.setOnApplyWindowInsetsListener(bottomNavLayout) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars())
            val baseHeightPx = (68 * resources.displayMetrics.density).toInt()

            val params = view.layoutParams
            params.height = baseHeightPx + insets.bottom
            view.layoutParams = params

            view.setPadding(view.paddingLeft, view.paddingTop, view.paddingRight, insets.bottom)
            windowInsets
        }

        val username = intent.getStringExtra("USER_NAME") ?: "User"
        val tvGreeting = findViewById<TextView>(R.id.tvGreeting)
        tvGreeting.text = "Halo $username,"

        // Header Profile Icon Click -> Open Profile Activity
        val imgHeaderProfile = findViewById<ImageView>(R.id.imgHeaderProfile)
        imgHeaderProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("USER_NAME", username)
            startActivity(intent)
        }

        // Bind Floating Cart Views
        layoutFloatingCart = findViewById(R.id.layoutFloatingCart)
        tvFloatingCartBadge = findViewById(R.id.tvFloatingCartBadge)
        tvFloatingCartItemCount = findViewById(R.id.tvFloatingCartItemCount)
        tvFloatingCartTotalPrice = findViewById(R.id.tvFloatingCartTotalPrice)
        btnViewCart = findViewById(R.id.btnViewCart)

        rvMenu = findViewById(R.id.rvMenu)
        rvMenu.layoutManager = LinearLayoutManager(this)

        currentDisplayedList.addAll(allMenuList)
        menuAdapter = MenuAdapter(currentDisplayedList) { selectedMenu ->
            addToCart(selectedMenu)
        }
        rvMenu.adapter = menuAdapter

        // Setup Category Filter Chips
        setupCategoryChips()

        // Setup Realtime Search Bar Filter
        val etSearch = findViewById<EditText>(R.id.etSearch)
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterMenu(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Open Bottom Sheet when Floating Cart is clicked
        val openCartListener = View.OnClickListener {
            showCartBottomSheet(username)
        }
        layoutFloatingCart.setOnClickListener(openCartListener)
        btnViewCart.setOnClickListener(openCartListener)

        val btnNavOrder = findViewById<View>(R.id.btnNavOrder)
        val btnNavProfile = findViewById<View>(R.id.btnNavProfile)

        btnNavOrder.setOnClickListener {
            val intent = Intent(this, AlamatActivity::class.java)
            intent.putExtra("USER_NAME", username)
            startActivity(intent)
        }

        btnNavProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("USER_NAME", username)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        updateFloatingCartUI()
    }

    private fun setupCategoryChips() {
        val chipSemua = findViewById<TextView>(R.id.chipCategorySemua)
        val chipKopi = findViewById<TextView>(R.id.chipCategoryKopi)
        val chipNonKopi = findViewById<TextView>(R.id.chipCategoryNonKopi)
        val chipCamilan = findViewById<TextView>(R.id.chipCategoryCamilan)

        val chips = listOf(
            Triple(chipSemua, "Semua", listOf("all")),
            Triple(chipKopi, "Kopi", listOf("Kopi", "Cappuccino")),
            Triple(chipNonKopi, "Non-Kopi", listOf("Matcha")),
            Triple(chipCamilan, "Camilan / Bakery", listOf("Donat", "Sourdough"))
        )

        fun selectChip(selectedTextView: TextView, categoryName: String) {
            selectedCategory = categoryName
            chips.forEach { (chipView, _, _) ->
                if (chipView == selectedTextView) {
                    chipView.setBackgroundResource(R.drawable.bg_chip_selected)
                    chipView.setTextColor(getColor(R.color.white))
                } else {
                    chipView.setBackgroundResource(R.drawable.bg_chip_unselected)
                    chipView.setTextColor(getColor(R.color.dark_espresso))
                }
            }
            filterMenu(findViewById<EditText>(R.id.etSearch).text.toString())
        }

        chipSemua.setOnClickListener { selectChip(chipSemua, "Semua") }
        chipKopi.setOnClickListener { selectChip(chipKopi, "Kopi") }
        chipNonKopi.setOnClickListener { selectChip(chipNonKopi, "Non-Kopi") }
        chipCamilan.setOnClickListener { selectChip(chipCamilan, "Camilan / Bakery") }
    }

    private fun filterMenu(query: String) {
        val trimmed = query.trim().lowercase()
        currentDisplayedList.clear()

        val categoryFiltered = when (selectedCategory) {
            "Kopi" -> allMenuList.filter { it.nama.contains("Kopi", ignoreCase = true) || it.nama.contains("Cappuccino", ignoreCase = true) }
            "Non-Kopi" -> allMenuList.filter { it.nama.contains("Matcha", ignoreCase = true) || it.nama.contains("Tea", ignoreCase = true) }
            "Camilan / Bakery" -> allMenuList.filter { it.nama.contains("Donat", ignoreCase = true) || it.nama.contains("Sourdough", ignoreCase = true) || it.nama.contains("Loaf", ignoreCase = true) }
            else -> allMenuList
        }

        if (trimmed.isEmpty()) {
            currentDisplayedList.addAll(categoryFiltered)
        } else {
            currentDisplayedList.addAll(categoryFiltered.filter {
                it.nama.lowercase().contains(trimmed) || it.deskripsi.lowercase().contains(trimmed)
            })
        }

        menuAdapter.notifyDataSetChanged()
    }

    private fun addToCart(menu: Menu) {
        CartManager.addToCart(menu)
        Toast.makeText(this, "${menu.nama} ditambahkan!", Toast.LENGTH_SHORT).show()
        updateFloatingCartUI()
    }

    private fun updateFloatingCartUI() {
        val totalCount = CartManager.getTotalCount()
        val totalPrice = CartManager.getTotalPrice()

        if (totalCount > 0) {
            layoutFloatingCart.visibility = View.VISIBLE
            tvFloatingCartBadge.text = totalCount.toString()
            tvFloatingCartItemCount.text = "$totalCount Item"
            tvFloatingCartTotalPrice.text = "Rp ${currencyFormatter.format(totalPrice)}"
        } else {
            layoutFloatingCart.visibility = View.GONE
        }
    }

    private fun showCartBottomSheet(username: String) {
        val dialog = BottomSheetDialog(this)
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_cart, null)
        dialog.setContentView(dialogView)

        val rvCartItems = dialogView.findViewById<RecyclerView>(R.id.rvCartItems)
        val tvEmptyCart = dialogView.findViewById<TextView>(R.id.tvEmptyCart)
        val layoutCartSummary = dialogView.findViewById<View>(R.id.layoutCartSummary)
        val tvBottomSheetTotalPrice = dialogView.findViewById<TextView>(R.id.tvBottomSheetTotalPrice)
        val btnCheckoutCart = dialogView.findViewById<Button>(R.id.btnCheckoutCart)

        rvCartItems.layoutManager = LinearLayoutManager(this)

        fun refreshBottomSheetUI() {
            val totalCount = CartManager.getTotalCount()
            val totalPrice = CartManager.getTotalPrice()

            if (totalCount == 0) {
                rvCartItems.visibility = View.GONE
                layoutCartSummary.visibility = View.GONE
                tvEmptyCart.visibility = View.VISIBLE
            } else {
                rvCartItems.visibility = View.VISIBLE
                layoutCartSummary.visibility = View.VISIBLE
                tvEmptyCart.visibility = View.GONE
                tvBottomSheetTotalPrice.text = "Rp ${currencyFormatter.format(totalPrice)}"
            }
            updateFloatingCartUI()
        }

        val cartAdapter = CartAdapter(CartManager.cartList) {
            refreshBottomSheetUI()
        }
        rvCartItems.adapter = cartAdapter

        refreshBottomSheetUI()

        btnCheckoutCart.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(this, AlamatActivity::class.java)
            intent.putExtra("USER_NAME", username)
            startActivity(intent)
        }

        dialog.show()
    }
}