package com.example.sayana.models;

public class News {
    private Long id;
    private String title;
    private String imageUrl;

    // Пустой конструктор (нужен для Gson)
    public News() {}

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}