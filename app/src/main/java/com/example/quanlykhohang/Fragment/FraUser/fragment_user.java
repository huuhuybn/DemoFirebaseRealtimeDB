package com.example.quanlykhohang.Fragment.FraUser;

import static android.content.Context.MODE_PRIVATE;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.quanlykhohang.Interface.TransFerFra;
import com.example.quanlykhohang.R;
import com.example.quanlykhohang.dao.userDAO;
import com.example.quanlykhohang.databinding.FragmentUserBinding;


public class fragment_user extends Fragment {
    private FragmentUserBinding binding;
    private userDAO dao;

    public fragment_user() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        var view = inflater.inflate(R.layout.fragment_user, container, false);
        binding = FragmentUserBinding.bind(view);

        phanQuyen();

        seticon();


        updatedUserInterface();

        binding.listUser.setOnClickListener(v -> {
            transferFragment(new listUser(), listUser.TAG);
        });

        binding.addUser.setOnClickListener(v -> {
            transferFragment(new AddUser(), AddUser.TAG);
        });

        binding.btnUpdate.setOnClickListener(v -> {
            transferFragment(new updateUser(), updateUser.TAG);
        });

        binding.changePassword.setOnClickListener(v -> {
            transferFragment(new changePassword(), changePassword.TAG);
        });

        return view;
    }

    private void seticon() {
        var actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_24);
    }

    private void updatedUserInterface() {
        var sharedPreferences = requireContext().getSharedPreferences("INFOR", MODE_PRIVATE);
        var id = sharedPreferences.getString("ID", "");
        dao = new userDAO(getContext());
        var list = dao.getAllUser(id);
        binding.imgavata.setImageURI(Uri.parse(list.get(0).getAvatar()));
        binding.txtNamePosi.setText(list.get(0).getName() + " (" + list.get(0).getPosition() + ")");
        binding.txtQue.setText(list.get(0).getHomeTown());
    }

    private void phanQuyen() {
        var sharedPreferences = requireContext().getSharedPreferences("INFOR", MODE_PRIVATE);
        var quyen = sharedPreferences.getString("POSITION", "");
        if (!quyen.equals("admin")) {
            binding.addUser.setVisibility(View.GONE);
        }
    }


    private void transferFragment(Fragment fragment,String name){
        ((TransFerFra) requireActivity()).transferFragment(fragment,name);
    }

}