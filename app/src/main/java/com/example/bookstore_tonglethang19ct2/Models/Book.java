package com.example.bookstore_tonglethang19ct2.Models;

import java.io.Serializable;

public class Book  implements Serializable {
    public String id;
    public String name, image, nhaxuatban, type, mota, idType;
    public int price,soluong;

    public Book(String id, String name, int price, String image, String nhaxuatban,int soluong, String type, String mota,String idType) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.nhaxuatban = nhaxuatban;
        this.type = type;
        this.mota = mota;
        this.price = price;
        this.soluong = soluong;
        this.idType = idType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNhaxuatban() {
        return nhaxuatban;
    }

    public void setNhaxuatban(String nhaxuatban) {
        this.nhaxuatban = nhaxuatban;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
