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
                                  @RequestParam("file") MultipartFile file) throws IOException {
        
        // Logika perhitungan ValueA (ValueB * Ratio / 100)
        if (transaction.getTracValueB() != null && transaction.getTracRatio() != null) {
            BigDecimal res = transaction.getTracValueB()
                .multiply(transaction.getTracRatio())
                .divide(new BigDecimal(100), RoundingMode.HALF_UP);
            transaction.setTracValueA(res);
        }

        // Proses gambar biner (imageBytes)
        if (!file.isEmpty()) {
            transaction.setTracPic(file.getBytes());
        }

        repository.save(transaction);
        // Mengarahkan ke parameter ?updated agar muncul notifikasi kuning di halaman menu/index
        return "redirect:/?updated";
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
        if (id != null) {
            // Mencari data di database berdasarkan TracId
            Transaction transaction = repository.findById(id).orElse(null);
            if (transaction != null) {
                model.addAttribute("transaction", transaction);
            } else {
                // Menampilkan pesan error jika ID tidak ditemukan
                model.addAttribute("error", true);
            }
        }
        return "edit-transaction"; // Mengarah ke file edit-transaction.html
    }
}