package com.example.quanlykhohang.model;

public class Product {
    int id;
    String name;//tên sản phẩm
    int quantity; // số lượng của sản phẩm
    int price; //giá của sản phẩm
    String photo; // hình ảnh của sản phẩm khi vào kho
    String storage; //lưu trữ
    String userID; // id của người tạo sản phẩm

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public Product(int quantity) {
        this.quantity = quantity;
    }

    public Product(String name,int price, int quantity,  String photo,String storage, String userID) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.photo = photo;
        this.storage = storage;
        this.userID = userID;
    }
    public Product(String name,int price, int quantity,  String photo, String userID) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.photo = photo;
        this.userID = userID;
    }

    public Product(int id, String name, int price, int quantity, String photo,String storage, String userID) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.photo = photo;
        this.storage = storage;
        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}

