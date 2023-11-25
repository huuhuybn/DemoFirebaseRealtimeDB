package com.example.quanlykhohang.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlykhohang.R;
import com.example.quanlykhohang.dao.userDAO;
import com.example.quanlykhohang.databinding.IteamListuserBinding;
import com.example.quanlykhohang.model.User;

import java.util.ArrayList;

public class userAdapter extends RecyclerView.Adapter<userAdapter.ViewHolder> {
    private Context context;
    private ArrayList<User> list;
    private userDAO dao;

    public userAdapter(Context context, ArrayList<User> list, userDAO dao) {
        this.context = context;
        this.list = list;
        this.dao = dao;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var inflater = LayoutInflater.from(parent.getContext());
        var view = inflater.inflate(R.layout.iteam_listuser, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.txtId.setText("ID: " + list.get(position).getId());
        holder.binding.txtNameUser.setText("Name: " + list.get(position).getName());
        holder.binding.txthomeTown.setText("Home town: " + list.get(position).getHomeTown());
        holder.binding.txtposition.setText("Position: " + list.get(position).getPosition());
        holder.binding.txtlastLogin.setText("Last login: " + list.get(position).getLastLogin());
        holder.binding.txtlastAction.setText("Last action: " + list.get(position).getLastAction());
        String photoPath = list.get(position).getAvatar();
        if (photoPath != null) {
            Uri uri = Uri.parse(photoPath);
            holder.binding.imgUser.setImageURI(uri);
        } else {
            holder.binding.imgUser.setImageResource(R.drawable.sontung);
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
        IteamListuserBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = IteamListuserBinding.bind(itemView);
        }
    }
}
