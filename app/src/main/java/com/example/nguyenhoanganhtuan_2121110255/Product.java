package com.example.nguyenhoanganhtuan_2121110255;

public class Product {
    private String name;
    private int price;
    private int image;
    String description;
    private String category;
    public Product(String name, int price, int image, String description, String category) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }
    public String getCategory() {
        return category;
    }
}
