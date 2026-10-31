package lat.pam.kopidannyemil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class KonfirmasiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konfirmasi)

        val username = intent.getStringExtra("USER_NAME") ?: "User"
        val tvGreeting = findViewById<TextView>(R.id.tvKonfirmasiGreeting)
        val btnOke = findViewById<Button>(R.id.btnOkeFinal)

        tvGreeting.text = "Halo $username,"

        btnOke.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("USER_NAME", username)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}