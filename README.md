# ☕ Kopi dan Nyemil - Aplikasi Pemesanan Kopi & Camilan Android

**Kopi dan Nyemil** adalah aplikasi pemesanan kopi dan makanan ringan (*bakery/camilan*) berbasis Android yang dirancang dengan antarmuka UI/UX modern, profesional, dan intuitif. Aplikasi ini mengusung tema warna khas *coffee shop* perpaduan *Dark Espresso*, *Warm Cream*, *Pure White*, dan *Accent Red*.

---

## 🌟 Fitur Utama

- 🔐 **Autentikasi & Registrasi Member**:
  - Fitur registrasi dan login anggota baru dengan penyimpanan data sesi otomatis (*SharedPreferences*).
  - Validasi form input interaktif.

- 🏠 **Halaman Utama & Katalog Menu**:
  - **Sapaan Pengguna Dinamis** (*Greeting User*).
  - **Banner Promo Harian Eksklusif** dengan desain gradien espresso.
  - **Live Search Bar (Pencarian Real-time)**: Memfilter daftar kopi dan camilan secara instan saat mengetik.
  - **Filter Kategori Chip**: Filter cepat (*Semua, Kopi, Non-Kopi, Camilan / Bakery*).
  - **Ikon Profil Header**: Navigasi cepat ke halaman profil pengguna.

- 🛒 **Floating Cart & Bottom Sheet Keranjang**:
  - Tombol Tambah (`+`) merah pada setiap item menu.
  - **Floating Cart Bar Melayang**: Muncul otomatis menampilkan total item dan total harga secara real-time.
  - **Bottom Sheet Dialog**: Melihat detail pesanan di keranjang, mengubah jumlah item (`-` `qty` `+`), dan menghapus pesanan.

- 🛵 **Checkout & Auto-Save Alamat Pengiriman**:
  - **Ringkasan Pesanan (Order Summary)**: Menampilkan rincian daftar menu yang dipesan beserta kalkulasi total pembayaran.
  - **Fitur Auto-Save & Auto-Load Alamat**: Alamat pengiriman dan detail patokan tersimpan secara otomatis menggunakan *SharedPreferences* sehingga tidak perlu mengetik ulang pada pemesanan berikutnya.

- ✅ **Konfirmasi Pesanan**:
  - Layar konfirmasi pesanan berhasil dengan indikator badge sukses dan informasi estimasi pengantaran kurir.

- 👤 **Pengaturan Profil Pengguna**:
  - Kelola nama tampilan (*Display Name*) dan preferensi akun pengguna.

- 🎨 **Adaptive App Icon & Design System**:
  - Ikon aplikasi Android kustom dengan vektor cangkir kopi dan uap aroma.
  - Komponen Material 3 dengan sudut melengkung (*rounded corners*) dan efek ripple.

---

## 🛠️ Teknologi yang Digunakan

| Komponen | Teknologi / Library |
| :--- | :--- |
| **Bahasa Pemrograman** | Kotlin |
| **UI Framework** | Native Android XML Layouts, Material Design 3 (`com.google.android.material`) |
| **Komponen UI Utama** | `RecyclerView`, `CardView`, `NestedScrollView`, `BottomSheetDialog`, `EditText`, `ImageView` |
| **Penyimpanan Lokal** | `SharedPreferences` (Auto-Save User & Address Data) |
| **Arsitektur & State** | Singleton Pattern (`CartManager`), Adapter Pattern (`MenuAdapter`, `CartAdapter`), Intent Extras |
| **Min SDK** | API Level 24 (Android 7.0 Nougat) |
| **Target SDK** | API Level 34 / 35 (Android 14 / 15) |
| **Build System** | Gradle (Kotlin DSL `build.gradle.kts`) |

---

## 📁 Struktur Proyek

```text
KopidanNyemil/
├── app/
│   ├── src/main/
│   │   ├── java/lat/pam/kopidannyemil/
│   │   │   ├── MainActivity.kt        # Landing Splash Screen
│   │   │   ├── WelcomeActivity.kt     # Screen pilihan Login / Register
│   │   │   ├── LoginActivity.kt       # Screen Login User
│   │   │   ├── RegisterActivity.kt    # Screen Registrasi Member
│   │   │   ├── HomeActivity.kt        # Dashboard Utama (Search, Category, Menu, Floating Cart)
│   │   │   ├── AlamatActivity.kt      # Form Checkout & Ringkasan Order (Auto-Save Alamat)
│   │   │   ├── KonfirmasiActivity.kt  # Screen Status Order Berhasil
│   │   │   ├── ProfileActivity.kt     # Screen Pengaturan Profil User
│   │   │   ├── Menu.kt                # Data Model Menu
│   │   │   ├── CartItem.kt            # Data Model Item Keranjang
│   │   │   ├── CartManager.kt         # Singleton State Manager Keranjang
│   │   │   ├── MenuAdapter.kt         # Adapter RecyclerView Katalog Menu
│   │   │   └── CartAdapter.kt         # Adapter RecyclerView Item Keranjang
│   │   └── res/
│   │       ├── drawable/              # Asset Vektor & Shape Custom Drawables
│   │       ├── layout/                # XML Layout Screens & Items
│   │       ├── mipmap-anydpi/         # Adaptive Launcher App Icons
│   │       └── values/                # Colors, Themes, Strings
│   └── build.gradle.kts               # Konfigurasi Build Module App
├── build.gradle.kts                   # Konfigurasi Build Root Project
└── README.md                          # Dokumentasi Proyek
```

---

## 🚀 Cara Menjalankan Aplikasi

### Prasyarat
- **Android Studio** (Versi Ladybug, Jellyfish, Hedgehog, atau yang lebih baru).
- **JDK 17** atau yang lebih baru.
- **Android Virtual Device (Emulator)** atau **Perangkat Android Fisik** (Android 7.0 Nougat / API 24 ke atas) dengan fitur *USB Debugging* aktif.

### Langkah-langkah
1. **Buka Proyek di Android Studio**:
   - Jalankan Android Studio, pilih **Open**, lalu arahkan ke folder proyek `KopidanNyemil`.
2. **Sinkronkan Gradle**:
   - Tunggu hingga proses *Gradle Sync* selesai. Jika diminta, klik **Sync Project with Gradle Files**.
3. **Jalankan Aplikasi**:
   - Pilih peranti Android (Emulator / HP Fisik) di bilah alat atas.
   - Klik tombol **Run 'app'** (atau tekan tombol pintas `Shift + F10`).

### Rakit APK Debug via Terminal (Opsional)
Anda juga dapat merakit berkas APK Debug melalui terminal perintah:
- **Windows (Command Prompt / PowerShell)**:
  ```cmd
  .\gradlew.bat assembleDebug
  ```
- **macOS / Linux**:
  ```bash
  ./gradlew assembleDebug
  ```
Hasil berkas APK berada di folder: `app/build/outputs/apk/debug/app-debug.apk`.

---

## 📄 Lisensi
Hak Cipta © 2025 Kopi dan Nyemil App. Dibuat untuk pemesanan kopi & camilan yang mudah dan menyenangkan. ☕🍪