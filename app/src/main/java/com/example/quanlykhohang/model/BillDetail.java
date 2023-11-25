package com.example.quanlykhohang.model;

public class BillDetail {
    int idProduct;// id sản phẩm
    int idBill;//id hóa đơn
    String nameProduct;//tên sản phẩm
    String quantity;//số lượng sản phẩm
    String price;//giá nhập
    int priceXuat;//giá xuất
    String createdDate;//ngày tạo hóa đơn

    public int getPriceXuat() {
        return priceXuat;
    }

    public void setPriceXuat(int priceXuat) {
        this.priceXuat = priceXuat;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getIdBill() {
        return idBill;
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public BillDetail(String createdDate,String nameProduct, String quantity, String price ) {
        this.nameProduct = nameProduct;
        this.quantity = quantity;
        this.price = price;
        this.createdDate = createdDate;
    }
    public BillDetail(String createdDate,String nameProduct, String quantity, int priceXuat) {
        this.nameProduct = nameProduct;
        this.quantity = quantity;
        this.priceXuat = priceXuat;
        this.createdDate = createdDate;
    }

    public BillDetail(int idProduct, int idBill, String quantity, String price,int priceXuat, String createdDate) {
        this.idProduct = idProduct;
        this.idBill = idBill;
        this.quantity = quantity;
        this.price = price;
        this.priceXuat = priceXuat;
        this.createdDate = createdDate;
    }

}
