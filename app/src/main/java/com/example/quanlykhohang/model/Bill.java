package com.example.quanlykhohang.model;

public class Bill {
    int id; //mã hóa đơn
    String quantity; // >0, nhập kho, <0 xuất kho
    String createdByUser; // id người tạo hóa đơn
    String createdDate; // ngày tạo hóa đơn
    String note; // ghi chú trường hợp đặc biệt

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Bill(String createdByUser,String quantity,  String createdDate, String note) {
        this.quantity = quantity;
        this.createdByUser = createdByUser;
        this.createdDate = createdDate;
        this.note = note;
    }

    public Bill(int id, String createdByUser, String quantity, String createdDate, String note) {
        this.id = id;
        this.quantity = quantity;
        this.createdByUser = createdByUser;
        this.createdDate = createdDate;
        this.note = note;
    }
}
