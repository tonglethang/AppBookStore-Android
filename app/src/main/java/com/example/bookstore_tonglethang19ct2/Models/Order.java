package com.example.bookstore_tonglethang19ct2.Models;

public class Order {
    public String idCus, nameBook;
    public int soluongBook;
    public long totalPriceBook;

    public Order(String idCus, String nameBook, int soluongBook, long totalPriceBook) {
        this.idCus = idCus;
        this.nameBook = nameBook;
        this.soluongBook = soluongBook;
        this.totalPriceBook = totalPriceBook;
    }

    public String getIdCus() {
        return idCus;
    }

    public void setIdCus(String idCus) {
        this.idCus = idCus;
    }


    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public int getSoluongBook() {
        return soluongBook;
    }

    public void setSoluongBook(int soluongBook) {
        this.soluongBook = soluongBook;
    }

    public long getTotalPriceBook() {
        return totalPriceBook;
    }

    public void setTotalPriceBook(long totalPriceBook) {
        this.totalPriceBook = totalPriceBook;
    }
}
