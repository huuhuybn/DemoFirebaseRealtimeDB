package com.example.quanlykhohang.Fragment.FraUser;

import static android.content.Context.MODE_PRIVATE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.quanlykhohang.Fragment.FraThongKe.thongkechitietngay;
import com.example.quanlykhohang.Interface.MenuController;
import com.example.quanlykhohang.R;
import com.example.quanlykhohang.dao.userDAO;
import com.example.quanlykhohang.database.DbHelper;
import com.example.quanlykhohang.databinding.FragmentChangePasswordBinding;

public class changePassword extends Fragment {
    public static final String TAG = changePassword.class.getName();
    private FragmentChangePasswordBinding binding;
    private userDAO base;

    public changePassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        var view = inflater.inflate(R.layout.fragment_change_password, container, false);
        binding = FragmentChangePasswordBinding.bind(view);
        setHasOptionsMenu(true);
        var actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        binding.btnChangePassword.setOnClickListener(v -> {
            changepass();
        });
        return view;
    }

    private void changepass() {
        var sharedPreferences = requireContext().getSharedPreferences("INFOR", MODE_PRIVATE);
        var id = sharedPreferences.getString("ID", "");
        var oldPassword = binding.edtOldPassword.getText().toString().trim();
        var newPassword = binding.edtNewPassword.getText().toString().trim();
        var newPassword2 = binding.edtConfirmPassword.getText().toString().trim();
        if (oldPassword.isEmpty() || newPassword.isEmpty() || newPassword2.isEmpty()) {
            Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            if (newPassword.equals(newPassword2)) {
                base = new userDAO(requireContext());
                var check = base.changePassword(id, oldPassword, newPassword);
                if (check == 1) {
                    Toast.makeText(requireContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    var base = new DbHelper(requireContext());
                    base.updateLastAction(id);
                    getParentFragmentManager().popBackStack();
                } else if (check == 0) {
                    Toast.makeText(requireContext(), "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Mật khẩu không được trùng nhau", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            requireActivity().getSupportFragmentManager().popBackStack();
            closeMenu();
            var actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_24);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void closeMenu() {
        ((MenuController) requireActivity()).closeMenu();
    }

}