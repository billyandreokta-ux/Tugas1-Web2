package com.example.Spring_mvc_lab.service;

import com.example.Spring_mvc_lab.model.Products;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    private final List<Products> products = new ArrayList<>();

    public ProductService() {
        // Data dummy — nanti akan diganti database di Week JPA
        products.add(new Products(1L, "Laptop ASUS", "Elektronik", 12_500_000, 15));
        products.add(new Products(2L, "Mouse Logitech", "Elektronik", 350_000, 50));
        products.add(new Products(3L, "Buku Java Programming", "Buku", 150_000, 30));
        products.add(new Products(4L, "Kopi Arabica 250g", "Makanan", 85_000, 100));
        products.add(new Products(5L, "Headphone Sony", "Elektronik", 1_200_000, 20));
        products.add(new Products(6L, "Novel Laskar Pelangi", "Buku", 75_000, 45));
    }

    public List<Products> findAll() {
        return products;
    }

    public Optional<Products> findById(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public List<Products> findByCategory(String category) {
        return products.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    public List<Products> search(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(lowerKeyword))
                .toList();
    }

    public List<String> getAllCategories() {
        return products.stream()
                .map(Products::getCategory)
                .distinct()
                .sorted()
                .toList();
    }

    public long countByCategory(String category) {
        return products.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .count();
    }

    public Products findMostExpensive() {
        return products.stream()
                .max((a, b) -> Double.compare(a.getPrice(), b.getPrice()))
                .orElse(null);
    }

    public Products findCheapest() {
        return products.stream()
                .min((a, b) -> Double.compare(a.getPrice(), b.getPrice()))
                .orElse(null);
    }

    public double getAveragePrice() {
        return products.stream()
                .mapToDouble(Products::getPrice)
                .average()
                .orElse(0);
    }

    public long countLowStock() {
        return products.stream()
                .filter(p -> p.getStock() < 20)
                .count();
    }
}