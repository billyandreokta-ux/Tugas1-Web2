# Latihan 1
## Eksperimen 1
Buka /test/view  → Apa yang muncul?   [ini dari @Controller]
Buka /test/text  → Apa yang muncul?   [ini dari @Controller + @ResponseBody --> text langsung]
Apa perbedaannya?                     /test/view me-render template HTML, /test/text return string mentah tanpa rendering.
Kesimpulan: @Controller tanpa @ResponseBody → return (nama template). Dengan @ResponseBody → return (data langsung).

## Eksperimen 2
Apakah berhasil?           [Tidak] 
HTTP Status code:          [500]
Error message:             
```
Whitelabel Error Page
This application has no explicit mapping for /error, so you are seeing this as a fallback.
Wed Feb 25 23:01:24 WIB 2026
There was an unexpected error (type=Internal Server Error, status=500).
```
Kesimpulan: Jika Controller return nama view yang tidak ada, Spring akan mengembalikan error 500 karena Thymeleaf Template Resolver tidak bisa menemukan file HTML yang diminta di folder templates/, dan exception ini tidak di-handle sehingga naik jadi 500.


## Eksperimen 3
| URL |    Hasil dari halaman    |
| :---: |:------------------------:|
| /greet | Selamat Pagi, Mahasiswa! |
| /greet?name=Budi |   Selamat Pagi, Budi!    |
| /greet?name=Budi&waktu=Siang |   Selamat Siang, Budi!   |
| /greet/Ani |        Halo, Ani!        |
| /greet/Ani/detail |        Halo, Ani!        |
| /greet/Ani/detail?lang=EN |       Hello, Ani!        |

**Pertanyaan:**
- URL mana yang pakai `@RequestParam`? **/greet, /greet?name=Budi, /greet?name=Budi&waktu=Siang**
- URL mana yang pakai `@PathVariable`? **/greet/Ani**
- URL mana yang pakai keduanya? **/greet/Ani/detail dan /greet/Ani/detail?lang=EN**

# Latihan 2
## Eksperimen 1
Apakah error? [YA]
Error message:
```
Whitelabel Error Page
This application has no explicit mapping for /error, so you are seeing this as a fallback.
Wed Feb 26 00:13:49 WIB 2026
There was an unexpected error (type=Internal Server Error, status=500).
```
Kesimpulan: Jika nama fragment salah, Thymeleaf akan melempar exception saat rendering dan mengembalikan HTTP 500, karena fragment tidak ditemukan di file yang direferensikan.

## Eksperimen 2
CSS masih bekerja? [YA]
Path yang salah :
Apakah halaman error? [Tidak]
Apakah CSS diterapkan? [Tidak]

Kesimpulan: th:href="@{}" lebih baik karena otomatis menyesuaikan context path kalau app di-deploy di sub-path, bukan selalu di root /. Jika file CSS tidak ada, halaman tetap muncul tapi tanpa styling.

