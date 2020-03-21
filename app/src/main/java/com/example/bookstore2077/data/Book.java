package com.example.bookstore2077.data;

import java.util.List;

public class Book {
    private String id;
    private String name;
    private String author;
    private float price;
    private String image_ref;
    private String category;
    private String ISBN;
    private float rating;
    private List<Comment> comments;




    public Book(String id, String name, String author, float price, String image_ref,
                String category, String ISBN, float rating, List<Comment> comments){
        this.id = id;
        this.name =name;
        this.author = author;
        this.price = price;
        this.image_ref = image_ref;
        this.category = category;
        this.ISBN = ISBN;
        this.rating = rating;
        this.comments = comments;
    }

    public Book(String id, String bookName, String author, float price, String coverImageLink, String category, float rating) {
        this.id = id;
        this.name = bookName;
        this.author = author;
        this.price = price;
        this.image_ref = coverImageLink;
        this.category = category;
        this.rating = rating;

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

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getISBN() {
        return ISBN;
    }

    public float getRating() {
        return rating;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
