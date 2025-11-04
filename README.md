# 🏋️ BMI Calculator App (Jetpack Compose, ViewModel & StateFlow)

[![GitHub language count](https://img.shields.io/github/languages/count/adlmii/BMI-App)](https://github.com/adlmii/BMI-App)
[![GitHub top language](https://img.shields.io/github/languages/top/adlmii/BMI-App)](https://github.com/adlmii/BMI-App)
[![GitHub last commit](https://img.shields.io/github/last-commit/adlmii/BMI-App)](https://github.com/adlmii/BMI-App/commits/main)

## 📝 Deskripsi Proyek

Aplikasi Mobile BMI (Body Mass Index) Calculator yang dikembangkan menggunakan **Jetpack Compose** dan arsitektur MVVM (Model-View-ViewModel). Proyek ini berfokus pada penerapan konsep *ViewModel* dan *StateFlow* untuk memastikan data aplikasi (input berat/tinggi dan hasil BMI) selalu aman dan tidak hilang meskipun layar dirotasi.

### Tujuan Utama Assignment yang Terpenuhi:

1.  **Form Input Data:** Implementasi form input untuk Berat Badan dan Tinggi Badan menggunakan `OutlinedTextField` dari Jetpack Compose.
2.  **Validasi menggunakan `ViewModel`:** Mekanisme validasi *input* dilakukan secara *real-time* di `AppViewModel`, memicu *error state* (`weightError`, `heightError`) dan menonaktifkan tombol hitung.
3.  **Proses/Submit Data Gabungan:** Fungsi `calculateBmi()` di `ViewModel` menggabungkan dua *input* (`weight` dan `height`) untuk menghasilkan nilai `BMI` dan `classification`.
4.  **Data Persistence:** Penggunaan `ViewModel` dan `StateFlow` memastikan data yang diinput pengguna **tidak hilang** saat terjadi perubahan orientasi layar (rotasi).

---

## ✨ Fitur dan Implementasi Teknikal

| Fitur | Implementasi | Keterangan |
| :--- | :--- | :--- |
| **Arsitektur** | **MVVM & UDF** | Menerapkan Unidirectional Data Flow (UDF) di mana data mengalir satu arah dari `ViewModel` ke UI. |
| **State Management** | **`StateFlow`** | Digunakan di `AppViewModel` untuk mengirimkan data UI (`UiState`) ke  secara real-time dari `ViewModel` ke UI. |
| **Persistence** | **`ViewModel`** | Objek `AppViewModel` dipertahankan oleh *framework* Android melewati perubahan konfigurasi, menjaga semua *state* input dan hasil perhitungan. |
| **Dynamic UI** | **`Card` & Color** | Hasil perhitungan ditampilkan dalam `Card` dengan warna latar belakang yang dinamis (misalnya, Hijau untuk Normal, Merah untuk Obesitas) dan Ikon yang berubah sesuai klasifikasi. |
| **Validasi** | **Real-Time** | Validasi memastikan input adalah angka yang valid (> 0) sebelum proses perhitungan diizinkan. |

---

## 📂 Struktur Kode

Manajemen *state* dipegang oleh tiga *file* utama di *package* `af.mobile.bmiapp.ui`:

* **`MainActivity.kt`**: Berisi Composable `BmiScreen`. Ini adalah **View** yang hanya membaca `UiState` dan mengirim *event* (`onClick`, `onValueChange`) ke `ViewModel`.
* **`AppViewModel.kt`**: Berisi semua *business logic*, validasi, dan fungsi perhitungan BMI (`calculateBmi()`). Bertindak sebagai **State Holder** utama.
* **`UiState.kt`**: `data class` yang mendefinisikan *state* lengkap aplikasi (input, error, dan hasil). Ini adalah **Model** dari layer UI.

---

## 🚀 Instalasi dan Run

1.  **Clone Repository:**
    ```bash
    git clone [https://github.com/adlmii/BMI-App.git](https://github.com/adlmii/BMI-App.git)
    ```
2.  **Buka di Android Studio:** Buka folder proyek (`BMI-App`) menggunakan Android Studio (Jellyfish 2023.3.1 atau lebih baru).
3.  **Sync & Run:** Lakukan Gradle Sync dan jalankan aplikasi di emulator atau perangkat fisik (min SDK 24).

---

## 🤝 Dibuat Oleh

* **Nama:** Aidil Fahmi
* **Mata Kuliah:** Pengembangan Aplikasi Mobile (PAM)

---
