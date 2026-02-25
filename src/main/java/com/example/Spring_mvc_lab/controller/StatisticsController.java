package com.example.Spring_mvc_lab.controller;

import com.example.Spring_mvc_lab.model.Product;
import com.example.Spring_mvc_lab.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StatisticsController {
    private final ProductService productService;

    public StatisticsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/statistics")
    public String statistics(Model model) {
        List<String> categories = productService.getAllCategories();

        // Total produk per kategori
        Map<String, Long> countPerCategory = new LinkedHashMap<>();
        for (String cat : categories) {
            countPerCategory.put(cat, productService.countByCategory(cat));
        }

        Product mostExpensive = productService.findMostExpensive();
        Product cheapest = productService.findCheapest();

        model.addAttribute("title", "Statistik Produk");
        model.addAttribute("totalProducts", productService.findAll().size());
        model.addAttribute("countPerCategory", countPerCategory);
        model.addAttribute("mostExpensive", mostExpensive);
        model.addAttribute("cheapest", cheapest);
        model.addAttribute("averagePrice", productService.getAveragePrice());
        model.addAttribute("lowStockCount", productService.countLowStock());

        return "statistics";
    }
}
