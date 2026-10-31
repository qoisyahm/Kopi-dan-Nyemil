package lat.pam.kopidannyemil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        var username = intent.getStringExtra("USER_NAME") ?: "User"

        val tvGreeting = findViewById<TextView>(R.id.tvProfileGreeting)
        val etProfileName = findViewById<EditText>(R.id.etProfileName)
        val btnSave = findViewById<Button>(R.id.btnSaveProfile)

        tvGreeting.text = "Halo $username,"
        etProfileName.setText(username)

        btnSave.setOnClickListener {
            val updatedName = etProfileName.text.toString()
            if (updatedName.isNotEmpty()) {
                username = updatedName
                tvGreeting.text = "Halo $username,"
                Toast.makeText(this, "Profil berhasil diperbarui!", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.btnNavHomeFromProfile).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("USER_NAME", username)
            startActivity(intent)
            finish()
        }

        findViewById<Button>(R.id.btnNavOrderFromProfile).setOnClickListener {
            val intent = Intent(this, AlamatActivity::class.java)
            intent.putExtra("USER_NAME", username)
            startActivity(intent)
        }
    }
}