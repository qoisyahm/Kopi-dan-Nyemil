package lat.pam.kopidannyemil

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val pref: SharedPreferences =
        context.getSharedPreferences("UserSessionPref", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_AUTH_TOKEN = "KEY_AUTH_TOKEN"
        private const val KEY_USER_NAME = "KEY_USER_NAME"
        private const val KEY_IS_LOGGED_IN = "KEY_IS_LOGGED_IN"
    }

    /**
     * Menyimpan sesi login beserta Auth Token
     */
    fun saveSession(token: String, username: String) {
        val editor = pref.edit()
        editor.putString(KEY_AUTH_TOKEN, token)
        editor.putString(KEY_USER_NAME, username)
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.apply()
    }

    /**
     * Memeriksa apakah pengguna sudah login & token masih valid
     */
    fun isLoggedIn(): Boolean {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false) && !getAuthToken().isNullOrEmpty()
    }

    /**
     * Mengambil Auth Token
     */
    fun getAuthToken(): String? {
        return pref.getString(KEY_AUTH_TOKEN, null)
    }

    /**
     * Mengambil Nama Pengguna
     */
    fun getUsername(): String {
        return pref.getString(KEY_USER_NAME, "User") ?: "User"
    }

    /**
     * Menghapus sesi login saat pengguna Logout
     */
    fun clearSession() {
        val editor = pref.edit()
        editor.clear()
        editor.apply()
    }
}