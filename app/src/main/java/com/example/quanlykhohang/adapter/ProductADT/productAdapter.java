package com.example.quanlykhohang.adapter.ProductADT;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlykhohang.Fragment.FraProduct.updateProduct;
import com.example.quanlykhohang.Interface.TransFerFra;
import com.example.quanlykhohang.R;
import com.example.quanlykhohang.dao.productDAO;
import com.example.quanlykhohang.databinding.ItemProductBinding;
import com.example.quanlykhohang.model.Product;

import java.util.ArrayList;

public class productAdapter extends RecyclerView.Adapter<productAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Product> list;
    private productDAO dao;
    private ProductAdapterListener listener;
    public interface ProductAdapterListener {
        void onEditProductClicked(Product product);
    }


    public productAdapter(Context context, ArrayList<Product> list, productDAO dao, ProductAdapterListener listener) {
        this.context = context;
        this.list = list;
        this.dao = dao;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var inflater = LayoutInflater.from(parent.getContext());
        var view = inflater.inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
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
        holder.binding.foldingCell.setOnClickListener(v -> {
            holder.binding.foldingCell.toggle(false);
        });

        holder.binding.btnLuuTru.setOnClickListener(view -> {
            new AlertDialog.Builder(context)
                    .setTitle("Thông báo")
                    .setMessage("Bạn có muốn lưu trữ sản phẩm này không?")
                    .setPositiveButton("Có", (dialogInterface, i) -> {
                        dao.updateProductStorage("0", list.get(position).getId());
                        list.remove(position);
                        notifyDataSetChanged();
                    })
                    .setNegativeButton("Không", null)
                    .show();
        });

        holder.binding.btnSua.setOnClickListener(view -> {
            listener.onEditProductClicked(list.get(position));
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemProductBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemProductBinding.bind(itemView);
        }
    }

    private void transferFragment(Fragment fragment,String name) {
        ((TransFerFra) context).transferFragment(fragment,name);
    }


}
