package lat.pam.kopidannyemil

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUsername = findViewById<EditText>(R.id.etLoginUsername)
        val etPassword = findViewById<EditText>(R.id.etLoginPassword)
        val btnLogin = findViewById<Button>(R.id.btnLoginSubmit)
        val tvToRegister = findViewById<TextView>(R.id.tvToRegister)

        btnLogin.setOnClickListener {
            val usernameInput = etUsername.text.toString().trim()
            val passwordInput = etPassword.text.toString().trim()

            // 1. Validasi form kosong
            if (usernameInput.isEmpty()) {
                Toast.makeText(this, "Masukkan username kamu!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sharedPref = getSharedPreferences("UserPref", MODE_PRIVATE)
            val savedUsername = sharedPref.getString("USERNAME", null)
            val savedPassword = sharedPref.getString("PASSWORD", null)
            val savedFullName = sharedPref.getString("FULL_NAME", usernameInput) ?: usernameInput

            // 2. Jika akun sudah pernah didaftarkan via Register, periksa passwordnya
            if (savedUsername != null && savedPassword != null) {
                if (usernameInput.equals(savedUsername, ignoreCase = true) && passwordInput == savedPassword) {
                    saveAndLoginUser(savedFullName)
                } else if (usernameInput.equals(savedUsername, ignoreCase = true) && passwordInput != savedPassword) {
                    Toast.makeText(this, "Password salah!", Toast.LENGTH_SHORT).show()
                } else {
                    // Pengguna baru/lain -> Langsung simpan & izinkan login
                    saveAndLoginUser(usernameInput)
                }
            } else {
                // Belum ada akun terdaftar -> Langsung simpan & izinkan login
                saveAndLoginUser(usernameInput)
            }
        }

        tvToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    private fun saveAndLoginUser(username: String) {
        val dummyToken = "Bearer_KopiNyemil_Token_" + System.currentTimeMillis()
        val sessionManager = SessionManager(this)
        sessionManager.saveSession(dummyToken, username)

        Toast.makeText(this, "Login Berhasil! Selamat Datang, $username", Toast.LENGTH_SHORT).show()
        navigateToHome(username)
    }

    private fun navigateToHome(fullName: String) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("USER_NAME", fullName)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}