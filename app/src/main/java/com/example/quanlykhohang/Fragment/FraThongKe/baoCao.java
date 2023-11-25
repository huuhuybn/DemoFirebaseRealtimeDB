package com.example.quanlykhohang.Fragment.FraThongKe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.quanlykhohang.Interface.MenuController;
import com.example.quanlykhohang.Interface.TransFerFra;
import com.example.quanlykhohang.R;
import com.example.quanlykhohang.databinding.FragmentBaoCaoBinding;


public class baoCao extends Fragment {
    private FragmentBaoCaoBinding binding;

    public baoCao() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        var view = inflater.inflate(R.layout.fragment_bao_cao, container, false);
        binding = FragmentBaoCaoBinding.bind(view);
        seticon();
        binding.addUser.setOnClickListener(view1 -> {
            transferFragment(new thongkeNgay(),thongkeNgay.TAG);
        });
        binding.changePassword.setOnClickListener(view1 -> {
            transferFragment(new thongkeThang(),thongkeThang.TAG);
        });
        binding.listUser.setOnClickListener(view1 -> {
            transferFragment(new thongKeNam(),thongKeNam.TAG);
        });
        return view;
    }

    private void transferFragment(Fragment fragment,String name) {
        ((TransFerFra) requireActivity()).transferFragment(fragment,name);
    }



    private void seticon() {
        var actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_24);
    }
}