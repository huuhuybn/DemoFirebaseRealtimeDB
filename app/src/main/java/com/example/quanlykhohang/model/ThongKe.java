package com.example.quanlykhohang.model;

public class ThongKe {
    private int id;
    private String date;
    private int tongVao;
    private int tongRa;

    public ThongKe(String date) {
        this.date = date;
    }

    private int tongDoanhThu;

    public ThongKe(int id, String date, int tongVao, int tongRa, int tongDoanhThu) {
        this.id = id;
        this.date = date;
        this.tongVao = tongVao;
        this.tongRa = tongRa;
        this.tongDoanhThu = tongDoanhThu;
    }

    public ThongKe(String date, int tongVao, int tongRa, int tongDoanhThu) {
        this.date = date;
        this.tongVao = tongVao;
        this.tongRa = tongRa;
        this.tongDoanhThu = tongDoanhThu;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTongDoanhThu() {
        return tongDoanhThu;
    }

    public void setTongDoanhThu(int tongDoanhThu) {
        this.tongDoanhThu = tongDoanhThu;
    }

    public ThongKe() {
    }

    public ThongKe(int tongDoanhThu) {
        this.tongDoanhThu = tongDoanhThu;
    }

    public ThongKe(String date, int tongVao, int tongRa) {
        this.date = date;
        this.tongVao = tongVao;
        this.tongRa = tongRa;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTongVao() {
        return tongVao;
    }

    public void setTongVao(int tongVao) {
        this.tongVao = tongVao;
    }

    public int getTongRa() {
        return tongRa;
    }

    public void setTongRa(int tongRa) {
        this.tongRa = tongRa;
    }
}
