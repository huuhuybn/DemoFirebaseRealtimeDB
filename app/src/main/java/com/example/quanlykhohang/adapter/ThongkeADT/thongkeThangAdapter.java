package com.example.quanlykhohang.adapter.ThongkeADT;

import static com.gun0912.tedpermission.provider.TedPermissionProvider.context;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.example.quanlykhohang.Fragment.FraThongKe.thongechitietthang;
import com.example.quanlykhohang.Fragment.FraThongKe.thongkechitietngay;
import com.example.quanlykhohang.Interface.TransFerFra;

import com.example.quanlykhohang.R;
import com.example.quanlykhohang.dao.thongKeDAO;
import com.example.quanlykhohang.databinding.ItemThongkethangBinding;
import com.example.quanlykhohang.model.ThongKe;

import java.util.ArrayList;


public class thongkeThangAdapter extends RecyclerView.Adapter<thongkeThangAdapter.ViewHolder>{
    private Context context;
    private ArrayList<ThongKe> list;
    private thongKeDAO dao;

    public thongkeThangAdapter(Context context, ArrayList<ThongKe> list, thongKeDAO dao) {
        this.context = context;
        this.list = list;
        this.dao = dao;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var inflater = LayoutInflater.from(parent.getContext());
        var view = inflater.inflate(R.layout.item_thongkethang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.txtNgay.setText("" + list.get(position).getDate());
        holder.binding.txtNhapKho.setText("" + list.get(position).getTongVao());
        holder.binding.txtXuatKho.setText("" + list.get(position).getTongRa());
        holder.binding.txtDoanhThu.setText("" + list.get(position).getTongDoanhThu());
        holder.binding.btnchitiet.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            Fragment fragment = new thongechitietthang();
            Bundle args = new Bundle();
            args.putString("date", "" + list.get(position).getDate());
            fragment.setArguments(args);
            transferFragment(fragment,thongechitietthang.TAG);
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemThongkethangBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemThongkethangBinding.bind(itemView);
        }
    }
    private void transferFragment(Fragment fragment,String name) {
        ((TransFerFra) context).transferFragment(fragment,name);
    }
}
