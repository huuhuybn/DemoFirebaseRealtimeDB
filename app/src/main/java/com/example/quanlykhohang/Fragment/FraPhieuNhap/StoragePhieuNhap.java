package com.example.quanlykhohang.Fragment.FraPhieuNhap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanlykhohang.Fragment.FraThongKe.thongkechitietngay;
import com.example.quanlykhohang.R;


public class StoragePhieuNhap extends Fragment {
    public static final String TAG = StoragePhieuNhap.class.getName();


    public StoragePhieuNhap() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_storage_phieu_nhap, container, false);
    }

}