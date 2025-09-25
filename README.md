# Proyek Business Application Development: FloraArtistry

Ini adalah proyek untuk mata kuliah **Business Application Development (ISYS6197)** di Universitas Bina Nusantara. Proyek ini berupa aplikasi desktop yang dibangun untuk sebuah toko bunga bernama **FloraArtistry** guna mengelola produk dan transaksi penjualan.

Aplikasi ini memiliki dua hak akses utama: **Admin** untuk manajemen produk dan **Customer** untuk proses pembelian.

---

## Fitur Utama

### Fitur Umum
- **Login & Registrasi**: Sistem autentikasi untuk membedakan peran pengguna.

### Fitur untuk Customer
- **Melihat Produk**: Menampilkan semua produk bunga yang tersedia dalam sebuah tabel.
- **Keranjang Belanja (Cart)**: Menambahkan produk ke keranjang, mengatur kuantitas, dan melihat total belanja.
- **Checkout**: Memproses produk di keranjang untuk membuat transaksi baru.

### Fitur untuk Admin
- **Manajemen Produk (CRUD)**: Admin dapat menambah, memperbarui (update), dan menghapus data produk bunga dari database.

---

## Teknologi & Lingkungan

- **Bahasa**: Java 11
- **Framework GUI**: JavaFX 17
- **Database**: MySQL (dijalankan melalui XAMPP)
- **IDE**: Eclipse 2020.6 R
- **Konektor**: MySQL Java Connection Library 8.0.24

**Penting**: Proyek ini dibuat sesuai batasan, yaitu **tanpa** menggunakan FXML, JavaFX Scene Builder, Regex, atau library eksternal di luar materi yang diajarkan.

---

## Cara Menjalankan Proyek

1.  **Setup Database**:
    -   Jalankan XAMPP (Apache & MySQL).
    -   Buat database baru di phpMyAdmin dengan nama `FloraArtistry`.
    -   Import file `create+insert.sql` yang telah disediakan ke dalam database tersebut.
2.  **Setup Proyek**:
    -   Buka proyek menggunakan Eclipse.
    -   Pastikan Java, JavaFX SDK, dan MySQL Connector Library sudah terkonfigurasi dengan benar di *project build path*.
3.  **Jalankan Aplikasi**:
    -   Temukan dan jalankan *main class* dari proyek.

---

## Akun Demo

Anda dapat menggunakan kredensial berikut untuk login:

-   **Admin**:
    -   **Email**: `admin@gmail.com`
    -   **Password**: `admin123`
-   **Customer**:
    -   **Email**: `customer@gmail.com`
    -   **Password**: `customer123`
