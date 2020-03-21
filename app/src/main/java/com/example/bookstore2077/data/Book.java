package com.example.bookstore2077.data;

public class Book {
    private String name;
    private String author;
    private double price;
    private String image_ref;

    public Book(String name, String author, double price, String image_ref){
        this.name =name;
        this.author = author;
        this.price = price;
        this.image_ref = image_ref;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getAuthor() {
        return author;
    }

    public String getImage_ref() {
        return image_ref;
    }
}
