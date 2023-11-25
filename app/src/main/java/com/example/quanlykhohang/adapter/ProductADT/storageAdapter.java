package com.example.quanlykhohang.adapter.ProductADT;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlykhohang.R;
import com.example.quanlykhohang.dao.productDAO;
import com.example.quanlykhohang.databinding.ItemStorageBinding;
import com.example.quanlykhohang.model.Product;

import java.util.ArrayList;

public class storageAdapter extends RecyclerView.Adapter<storageAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Product> list;
    private productDAO dao;

    public storageAdapter(Context context, ArrayList<Product> list, productDAO dao) {
        this.context = context;
        this.list = list;
        this.dao = dao;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var inflater = LayoutInflater.from(parent.getContext());
        var view = inflater.inflate(R.layout.item_storage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.txtmaSP.setText("Mã sản phẩm: " + list.get(position).getId());
        holder.binding.txtTenSP.setText("Tên sản phẩm: " + list.get(position).getName());
        holder.binding.txtGiaSP.setText("Giá sản phẩm: " + list.get(position).getPrice());
        holder.binding.txtSoLuong.setText("Số lượng: " + list.get(position).getQuantity());
        holder.binding.txtNguoiThem.setText("Người thêm: " + list.get(position).getUserID());
        String photoPath = list.get(position).getPhoto();
        if (photoPath != null) {
            Uri uri = Uri.parse(photoPath);
            holder.binding.imgHinhSP.setImageURI(uri);
        } else {
            holder.binding.imgHinhSP.setImageResource(droidninja.filepicker.R.drawable.image_placeholder); // Đặt hình ảnh mặc định (R.drawable.default_image là ID của hình mặc định)
        }
    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemStorageBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemStorageBinding.bind(itemView);
        }
    }
}
