package lat.pam.kopidannyemil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cek Auto-Login (Persistent Session) saat aplikasi dibuka
        val sessionManager = SessionManager(this)
        if (sessionManager.isLoggedIn()) {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("USER_NAME", sessionManager.getUsername())
            startActivity(intent)
            finish()
            return
        }

        setContentView(R.layout.activity_main)

        val btnStartNow = findViewById<Button>(R.id.btnStartNow)
        btnStartNow.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
        }
    }
}