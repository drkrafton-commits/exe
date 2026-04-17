package com.example.sayana.models;

import java.math.BigDecimal;

public class Product {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private String gender;
    private String imageUrl;

    public Product() {}

    // Геттеры
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public String getCategory() { return category; }
    public String getGender() { return gender; }
    public String getImageUrl() { return imageUrl; }

    // Сеттеры
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setCategory(String category) { this.category = category; }
    public void setGender(String gender) { this.gender = gender; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}