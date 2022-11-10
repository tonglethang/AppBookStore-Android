package com.example.bookstore_tonglethang19ct2.Utils;

public class Server {
    public  static String localhost = "192.168.1.18:5035";
    public static  String linkTypeBook = "http://" +  localhost + "/typebook/all";
    public  static  String linkAllBook = "http://" +  localhost + "/allbook";
    public static  String linkBooks = "http://" +  localhost + "/book/";
    public static  String linkCustomer = "http://" +  localhost + "/createCus";
    public static  String linkDonhang = "http://" +  localhost + "/createDonhang";
//admin
    public static  String linkAllBookAdmin = "http://" +  localhost + "/allbookadmin";
    public static  String linkAllCustomerAdmin = "http://" +  localhost + "/customeradmin";
    public static  String linkDeleteCustomerAdmin = "http://" +  localhost + "/deleteCus";
    public static  String linkAddBook = "http://" +  localhost + "/addBook";
}
