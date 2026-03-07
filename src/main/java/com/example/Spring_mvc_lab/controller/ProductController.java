package com.example.Spring_mvc_lab.controller;

import com.example.Spring_mvc_lab.model.Products;
import com.example.Spring_mvc_lab.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    // Constructor Injection (Week 3 pattern)
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // GET /products → tampilkan semua produk

    @GetMapping
    public String listProducts(Model model) {  // ← Model object (Baki Pengantar)
        //Controller memanggil productService.findAll() lalu service mengembalikan list<product> yang berisi 6 produk tadi
        List<Products> products = productService.findAll();  // ← dari Service (Model Layer/Dapur)

        //Controler akan memasukan data ke model menggunakan model.addAtribute
        model.addAttribute("products", products);       // ← taruh Data Class di Baki
        model.addAttribute("title", "Daftar Produk");
        model.addAttribute("totalProducts", products.size());

        //Controller me-return product itu Spring merender list.html
        return "products/list";  // → templates/product/list.html
    }

    // GET /products/42 → tampilkan detail produk
    @GetMapping("/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        Optional<Products> product = productService.findById(id);

        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            model.addAttribute("title", "Detail: " + product.get().getName());
        } else {
            model.addAttribute("error", "Produk dengan ID " + id + " tidak ditemukan");
            model.addAttribute("title", "Produk Tidak Ditemukan");
        }

        return "products/detail";  // → templates/product/detail.html
    }

    // GET /products/category/Elektronik → filter by category
    @GetMapping("/category/{category}")
    public String productsByCategory(@PathVariable String category, Model model) {
        List<Products> products = productService.findByCategory(category);

        model.addAttribute("products", products);
        model.addAttribute("title", "Kategori: " + category);
        model.addAttribute("totalProducts", products.size());
        model.addAttribute("selectedCategory", category);

        return "products/list";  // reuse template yang sama!
    }

    // GET /products/search?keyword=laptop → search produk
    @GetMapping("/search")
    public String searchProducts(@RequestParam(defaultValue = "") String keyword,
                                 Model model) {
        List<Products> products = keyword.isBlank()
                ? productService.findAll()
                : productService.search(keyword);

        model.addAttribute("products", products);
        model.addAttribute("title", "Hasil Pencarian: " + keyword);
        model.addAttribute("totalProducts", products.size());
        model.addAttribute("keyword", keyword);

        return "products/list";  // reuse template yang sama!
    }

    @GetMapping("/categories")
    public String categorySummary(Model model) {
        List<String> categories = productService.getAllCategories();

        Map<String, Long> categoryCount = new LinkedHashMap<>();
        for (String cat : categories) {
            categoryCount.put(cat, productService.countByCategory(cat));
        }

        model.addAttribute("categoryCount", categoryCount);
        model.addAttribute("title", "Ringkasan Kategori");
        return "products/categories";
    }

}