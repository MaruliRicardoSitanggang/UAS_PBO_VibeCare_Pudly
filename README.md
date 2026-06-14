# VibeCare - Aplikasi Kesehatan Mental

**Deskripsi Singkat Aplikasi**
  VibeCare adalah aplikasi kesehatan mental berbasis desktop yang dikembangkan untuk menjawab tantangan meningkatnya masalah kesehatan mental di masyarakat Indonesia. Aplikasi ini menyediakan platform yang aman, nyaman, dan mudah diakses bagi siapa saja yang ingin menjaga kesehatan mentalnya.
  VibeCare hadir sebagai solusi atas keterbatasan akses terhadap layanan psikolog profesional, tingginya biaya konsultasi, serta stigma negatif yang masih melekat pada isu kesehatan mental. Dengan menggabungkan berbagai fitur seperti konsultasi online, tes kesehatan mental, komunitas dukungan anonim, panduan meditasi, dan sistem reward poin, VibeCare bertujuan untuk membuat layanan kesehatan mental lebih terjangkau dan mudah dijangkau oleh seluruh lapisan masyarakat.
  Aplikasi ini dibangun dengan pendekatan Model-View-Controller (MVC) menggunakan JavaFX untuk antarmuka pengguna dan Spring Boot untuk backend REST API, sehingga menjamin kinerja yang optimal, keamanan data, dan kemudahan pengembangan lebih lanjut.

**Fitur-Fitur Utama**
1. Tes Kesehatan Mental
Fitur ini memungkinkan pengguna untuk melakukan deteksi dini terhadap potensi gangguan mental melalui kuesioner yang telah divalidasi secara klinis:
Tes Depresi (PHQ-9) : 9 pertanyaan untuk mengevaluasi gejala depresi dalam 2 minggu terakhir
Tes Kecemasan (GAD-7) : 7 pertanyaan untuk mengukur tingkat kecemasan yang dialami
Tes Tingkat Stres : 10 pertanyaan untuk mengetahui seberapa berat beban stres
  Setelah menyelesaikan tes, pengguna akan mendapatkan hasil berupa skor, interpretasi hasil, serta rekomendasi tindakan yang sesuai dengan tingkat keparahan yang terdeteksi. Hasil tes juga tersimpan dalam riwayat pengguna untuk memantau perkembangan kesehatan mental dari waktu ke waktu.

2. Konsultasi Psikolog
Fitur yang menghubungkan pengguna dengan psikolog profesional secara online:
Daftar Psikolog : Menampilkan profil psikolog lengkap dengan spesialisasi, pengalaman, rating, dan jadwal praktik
Metode Konsultasi : Pilihan konsultasi melalui chat, telepon, atau video call sesuai preferensi pengguna
Booking Jadwal : Sistem pemesanan jadwal konsultasi dengan psikolog pilihan
Sistem Diskon : Penggunaan koin untuk mendapatkan diskon hingga 50% dari harga konsultasi
Riwayat Konsultasi : Melihat jadwal konsultasi yang telah dan akan datang

3. Komunitas Anonim
Fitur ini menyediakan ruang aman bagi pengguna untuk berbagi pengalaman dan mendapatkan dukungan dari sesama:
Posting Anonim : Semua cerita ditampilkan secara anonim untuk melindungi privasi pengguna
Filter Topik : Pengguna dapat memfilter postingan berdasarkan topik (Pencapaian, Meditasi, Dukungan, Curhat, Motivasi)
Interaksi Sosial : Fitur like dan komentar pada postingan untuk memberikan dukungan
Pencarian : Mencari cerita berdasarkan kata kunci
Tips dari Admin : Konten edukasi yang diposting oleh admin untuk meningkatkan literasi kesehatan mental

5.  Meditasi & Relaksasi
Fitur yang membantu pengguna menenangkan pikiran melalui berbagai panduan meditasi:
Panduan Meditasi : Step-by-step guidance untuk berbagai jenis meditasi (Pernapasan, Tidur Nyenyak, Redakan Kecemasan, Mindfulness)
Timer Meditasi : Timer dengan durasi yang dapat disesuaikan
Musik Relaksasi : Koleksi musik dan suara alam untuk menciptakan suasana yang menenangkan
Tracking Sesi : Riwayat sesi meditasi yang telah dilakukan

5.  Misi Harian
Fitur gamifikasi yang memotivasi pengguna untuk rutin menjaga kesehatan mental:
Login Harian : +10 poin setiap hari
Tes Kesehatan Mental : +25 poin setiap menyelesaikan tes
Berbagi Cerita : +15 poin setiap posting di komunitas
Meditasi 10 Menit : +20 poin setiap sesi meditasi
Tracking Progress : Melihat misi mana yang sudah dan belum diselesaikan

6.  Sistem Poin & Reward
Sistem reward yang mendorong partisipasi aktif pengguna:
Akumulasi Poin : Poin terkumpul dari penyelesaian misi harian
Konversi Poin : 100 koin = Rp 10.000 diskon konsultasi
Maksimal Diskon : Diskon maksimal 50% dari harga konsultasi
Notifikasi : Pemberitahuan saat poin bertambah atau digunakan

7.  Admin Panel
Fitur khusus untuk administrator aplikasi:
Dashboard Admin : Menampilkan statistik pengguna, psikolog, postingan, dan laporan
Kelola Pengguna : Melihat, menonaktifkan, atau menghapus akun pengguna
Kelola Psikolog : Menambah, mengedit, atau menghapus data psikolog
Moderasi Postingan : Menyetujui, menolak, atau menghapus postingan yang melanggar aturan
Laporan Pengguna : Menangani laporan dari pengguna
Log Aktivitas : Melihat riwayat aktivitas semua pengguna

8.  Psikolog Panel
Fitur khusus untuk psikolog yang terdaftar dalam sistem:
Dashboard Psikolog : Menampilkan ringkasan jadwal konsultasi dan statistik pasien
Kelola Jadwal : Mengatur jadwal praktik (hari dan jam tersedia)
Lihat Booking : Melihat daftar permintaan konsultasi dari pengguna
Konfirmasi Konsultasi : Menerima atau menolak jadwal konsultasi
Riwayat Pasien : Melihat riwayat konsultasi dengan pasien
Update Status : Mengubah status online/offline
Profil Psikolog : Mengedit profil dan spesialisasi

9.  Keamanan & Privasi
Fitur keamanan yang melindungi data pengguna:
Role-Based Access : Tiga level akses (USER, ADMIN, PSYCHOLOGIST)
Autentikasi : Login dengan email dan password
Anonimitas Komunitas : Postingan komunitas tidak menampilkan identitas asli
Enkripsi Password : Password disimpan dalam bentuk terenkripsi di database
Session Management : Pengelolaan session pengguna yang aman

**Cara Menjalankan Aplikasi** 
- Menggunakan JDK 21
- Maven 3.8 atau lebih baru
-Git untuk clone repositori


**Langkah 1:** Clone Repositori
bash
git clone https://github.com/username/CareVibe.git
cd CareVibe

**Langkah 2:** Menjalankan Backend (Spring Boot)
bash
cd CareVibe-BE
mvn clean install
mvn spring-boot:run
Backend akan berjalan di http://localhost:8080

**Langkah 3:** Menjalankan Frontend (JavaFX)
bash
cd CareVibe-FE
mvn clean javafx:run

**Langkah 4**: Login ke Aplikasi
Role	Email	Password
Administrator	admin@vibecare.com	admin123
Pengguna Biasa	user@example.com	password123
Psikolog	psikolog@vibecare.com	psikolog123
Pengguna baru dapat mendaftar melalui halaman "Daftar" pada aplikasi.

**Dependencies yang Digunakan:**
1. Frontend (JavaFX): JavaFX21
2. Jackson Databind 2.15.2
3. Backend (Spring Boot) Dependencies: 
  - Spring Boot Starter Web
  - Spring Boot Starter Data JPA
  - Spring Boot Starter Security
  - Spring Boot Starter Validation
  - H2 Database
  - Hibernate

📹 Link Video Presentasi YouTube
[Klik di sini untuk menonton](https://youtu.be/rgB9yuqbMMA?si=wkuyn4RZW4SMCfh9)

💚 VibeCare - Menjaga Mental, Meraih Bahagia 💚
