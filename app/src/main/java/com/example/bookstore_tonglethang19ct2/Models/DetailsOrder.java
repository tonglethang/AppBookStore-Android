package com.example.bookstore_tonglethang19ct2.Models;

public class DetailsOrder {
    public String name, image;
    int quantity;
    long price;

    public DetailsOrder(String name,String image, int quantity, long price) {
        this.name = name;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
