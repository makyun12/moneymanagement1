package com.project.moneymanagement;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class TransactionController {

    @Autowired
    private TransactionRepository repository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

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
        return "redirect:/?success";
    }
}