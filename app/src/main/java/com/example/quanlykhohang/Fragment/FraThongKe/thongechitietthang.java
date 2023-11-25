package com.example.quanlykhohang.Fragment.FraThongKe;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanlykhohang.Interface.MenuController;
import com.example.quanlykhohang.Interface.TransFerFra;
import com.example.quanlykhohang.R;
import com.example.quanlykhohang.adapter.PhieuNhapADT.BillDeatailAdapter;
import com.example.quanlykhohang.adapter.PhieuXuatADT.BillDetailPXAdapter;
import com.example.quanlykhohang.adapter.ThongkeADT.thongkeThangAdapter;
import com.example.quanlykhohang.dao.billDetailDAO;
import com.example.quanlykhohang.dao.thongKeDAO;
import com.example.quanlykhohang.databinding.FragmentThongechitietthangBinding;
import com.example.quanlykhohang.model.BillDetail;
import com.example.quanlykhohang.model.ThongKe;

import java.util.ArrayList;

public class thongechitietthang extends Fragment {
    public static final String TAG = thongechitietthang.class.getName();
    private FragmentThongechitietthangBinding binding;
    Bundle args;
    private billDetailDAO daoCTPN;
    private thongKeDAO dao;
    public thongechitietthang() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        var view = inflater.inflate(R.layout.fragment_thongechitietthang, container, false);
        binding = FragmentThongechitietthangBinding.bind(view);
        var actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);

        args = getArguments();
        var date = args.getString("date");
        daoCTPN = new billDetailDAO(requireContext());

        binding.rcviewNhap.setLayoutManager(new LinearLayoutManager(requireContext()));
        var list = daoCTPN.getBillDetailsForMonth(date);
        var adapter = new BillDeatailAdapter(requireContext(), (ArrayList<BillDetail>) list, daoCTPN);
        binding.rcviewNhap.setAdapter(adapter);


        binding.rcviewXuat.setLayoutManager(new LinearLayoutManager(requireContext()));
        var listt = daoCTPN.getBillDetailsForMonthX(date);
        var adapte = new BillDetailPXAdapter(requireContext(), (ArrayList<BillDetail>) listt, daoCTPN);
        binding.rcviewXuat.setAdapter(adapte);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            requireActivity().getSupportFragmentManager().popBackStack();
            closeMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void transferFragment(Fragment fragment,String name) {
        ((TransFerFra) requireActivity()).transferFragment(fragment,name);
    }
    private void closeMenu() {
        ((MenuController) requireActivity()).closeMenu();
    }
}