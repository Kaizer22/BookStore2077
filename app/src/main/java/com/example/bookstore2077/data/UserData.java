package com.example.bookstore2077.data;

import java.util.ArrayList;

public class UserData {
    public static ArrayList<String> readBooks;
    public static ArrayList<String> booksToBuy;

    public static void readBook(String id){
        readBooks.add(id);
    }

    public static void forgetBook(String id){
        readBooks.remove(id);
    }

    public static void buyBook(String id){
        booksToBuy.add(id);
    }
}
