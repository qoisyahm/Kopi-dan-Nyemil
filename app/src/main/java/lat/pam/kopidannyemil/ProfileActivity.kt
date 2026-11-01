package lat.pam.kopidannyemil

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Penanganan System Navigation Bar Window Insets secara akurat
        val bottomNavProfile = findViewById<View>(R.id.bottomNavProfile)
        ViewCompat.setOnApplyWindowInsetsListener(bottomNavProfile) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars())
            val baseHeightPx = (68 * resources.displayMetrics.density).toInt()

            val params = view.layoutParams
            params.height = baseHeightPx + insets.bottom
            view.layoutParams = params

            view.setPadding(view.paddingLeft, view.paddingTop, view.paddingRight, insets.bottom)
            windowInsets
        }

        val sessionManager = SessionManager(this)
        var username = intent.getStringExtra("USER_NAME")
            ?: sessionManager.getUsername()

        val tvGreeting = findViewById<TextView>(R.id.tvProfileGreeting)
        val etProfileName = findViewById<EditText>(R.id.etProfileName)
        val btnSave = findViewById<Button>(R.id.btnSaveProfile)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        tvGreeting.text = "Halo $username,"
        etProfileName.setText(username)

        btnSave.setOnClickListener {
            val updatedName = etProfileName.text.toString().trim()
            if (updatedName.isNotEmpty()) {
                username = updatedName
                tvGreeting.text = "Halo $username,"

                // Simpan perubahan ke SessionManager & UserPref
                val sharedPref = getSharedPreferences("UserPref", MODE_PRIVATE)
                val token = sessionManager.getAuthToken() ?: "Bearer_Token"
                sessionManager.saveSession(token, username)

                sharedPref.edit().putString("FULL_NAME", username).apply()

                Toast.makeText(this, "Profil berhasil diperbarui!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Nama tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
        }

        btnLogout.setOnClickListener {
            sessionManager.clearSession()
            Toast.makeText(this, "Berhasil Logout!", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        findViewById<View>(R.id.btnNavHomeFromProfile).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("USER_NAME", username)
            startActivity(intent)
            finish()
        }

        findViewById<View>(R.id.btnNavOrderFromProfile).setOnClickListener {
            val intent = Intent(this, AlamatActivity::class.java)
            intent.putExtra("USER_NAME", username)
            startActivity(intent)
        }
    }
}