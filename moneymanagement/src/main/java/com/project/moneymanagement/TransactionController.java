package com.project.moneymanagement;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class TransactionController {

    @Autowired
    private TransactionRepository repository;

    @PostMapping("/save")
    public String saveTransaction(@ModelAttribute Transaction transaction, 
                                  @RequestParam("file") MultipartFile file,
                                  Model model) throws IOException { // Gunakan Model, bukan RedirectAttributes
        try {
            // Logika perhitungan tetap sama
            if (transaction.getTracValueB() != null && transaction.getTracRatio() != null) {
                BigDecimal res = transaction.getTracValueB()
                    .multiply(transaction.getTracRatio())
                    .divide(new BigDecimal(100), RoundingMode.HALF_UP);
                transaction.setTracValueA(res);
            }

            if (!file.isEmpty()) {
                transaction.setTracPic(file.getBytes());
            }

            repository.save(transaction);
            
            // Kirim sinyal berhasil ke halaman yang sama
            model.addAttribute("status", "sukses");
            model.addAttribute("pesan", "Pendaftaran Transaksi Berhasil!");
            
        } catch (Exception e) {
            model.addAttribute("status", "gagal");
            model.addAttribute("pesan", "Terjadi kesalahan: " + e.getMessage());
        }

        // Ambil data terbaru lagi supaya tabel di bawah tetap terisi setelah refresh
        model.addAttribute("listData", repository.findAll()); 
        
        return "index"; // Langsung balik ke file index.html tanpa "redirect:"
    }

    @GetMapping("/")
    public String index() {
        return "menu";
    }

    @GetMapping("/transaction")
    public String transactionPage() {
        return "index"; // index.html berisi form pendaftaran transaksi
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // Fitur Baru: Pencarian data berdasarkan ID untuk proses edit (取引修正)
    @GetMapping("/edit-search")
    public String searchForEdit(@RequestParam(value = "id", required = false) String id, Model model) {
        if (id != null && !id.isEmpty()) {
            // Mencari data di database berdasarkan TracId
            Transaction transaction = repository.findById(id).orElse(null);
            
            if (transaction != null) {
                model.addAttribute("transaction", transaction);
                // Menampilkan notifikasi bahwa data ditemukan dan siap diedit
                model.addAttribute("status", "sukses");
                model.addAttribute("pesan", "データが見つかりました。編集可能です (Data ditemukan, silakan edit)");
            } else {
                // Jika ID tidak ada di database, kirim pesan error ke area yang dilingkari
                model.addAttribute("status", "gagal");
                model.addAttribute("pesan", "ID: " + id + " は見つかりませんでした (ID tidak ditemukan)");
            }
        }
        
        // Pastikan data tabel tetap muncul jika Anda menampilkan daftar di bawahnya
        model.addAttribute("listData", repository.findAll()); 
        
        return "edit-transaction"; // Mengarah ke file edit-transaction.html
    }
}