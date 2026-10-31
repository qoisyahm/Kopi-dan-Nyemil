package lat.pam.kopidannyemil

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUsername = findViewById<EditText>(R.id.etLoginUsername)
        val etPassword = findViewById<EditText>(R.id.etLoginPassword)
        val btnLogin = findViewById<Button>(R.id.btnLoginSubmit)

        btnLogin.setOnClickListener {
            val usernameInput = etUsername.text.toString().trim()
            val passwordInput = etPassword.text.toString().trim()

            // 1. Validasi form kosong
            if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
                Toast.makeText(this, "Masukkan username dan password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 2. Ambil data yang tersimpan di SharedPreferences (Default: admin / 1234)
            val sharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
            val savedUsername = sharedPref.getString("USERNAME", "admin")
            val savedPassword = sharedPref.getString("PASSWORD", "1234")
            val savedFullName = sharedPref.getString("FULL_NAME", "User Kopi")

            // 3. Periksa kecocokan Username & Password
            if (usernameInput == savedUsername && passwordInput == savedPassword) {
                Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("USER_NAME", savedFullName)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Username atau password salah!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}