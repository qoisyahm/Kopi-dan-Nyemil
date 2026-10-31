package lat.pam.kopidannyemil

data class Menu(
    val nama: String,
    val deskripsi: String,
    val harga: String,
    val gambarResId: Int
) {
    fun getHargaNumeric(): Int {
        val clean = harga.replace("[^0-9]".toRegex(), "")
        return clean.toIntOrNull() ?: 0
    }
}