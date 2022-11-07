package com.example.bookstore_tonglethang19ct2.Models;

public class Cart {
    public  String idBook;
    public  String name;
    public long price;
    public String image;
    public int quantity, quanMax;

    public Cart(String idBook, String name, long price, String image, int quantity, int quanMax) {
        this.idBook = idBook;
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.quanMax = quanMax;
    }

    public String getIdBook() {
        return idBook;
    }

    public void setIdBook(String idBook) {
        this.idBook = idBook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getQuanMax() {
        return quanMax;
    }

    public void setQuanMax(int quanMax) {
        this.quanMax = quanMax;
    }
}
