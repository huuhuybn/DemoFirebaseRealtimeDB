package com.example.quanlykhohang.Fragment.FraUser;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.quanlykhohang.Fragment.FraThongKe.thongkechitietngay;
import com.example.quanlykhohang.Interface.MenuController;
import com.example.quanlykhohang.R;
import com.example.quanlykhohang.dao.userDAO;
import com.example.quanlykhohang.database.DbHelper;
import com.example.quanlykhohang.databinding.FragmentUpdateUserBinding;

import java.util.ArrayList;
import java.util.List;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class updateUser extends Fragment implements EasyPermissions.PermissionCallbacks {
    public static final String TAG = updateUser.class.getName();
    ArrayList<Uri> listUri = new ArrayList<>();
    private FragmentUpdateUserBinding binding;
    private userDAO dao;

    public updateUser() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        var view = inflater.inflate(R.layout.fragment_update_user, container, false);
        binding = FragmentUpdateUserBinding.bind(view);
        setIcon();
        updateUserInterface();
        binding.btnUpdateUser.setOnClickListener(view1 -> {
            editProfile();
        });
        binding.imgUser.setOnClickListener(v -> {
            requestPermission();
        });

        return view;
    }

    private void setIcon() {
        setHasOptionsMenu(true);
        var actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
    }

    private void updateUserInterface() {
        var sharedPreferences = requireContext().getSharedPreferences("INFOR", MODE_PRIVATE);
        var id = sharedPreferences.getString("ID", "");
        dao = new userDAO(getContext());
        var list = dao.getAllUser(id);
        binding.txtNameUser.setText(list.get(0).getName());
        binding.txtposition.setText(list.get(0).getPosition());
        binding.txthomeTown.setText(list.get(0).getHomeTown());
        binding.imgUser.setImageURI(Uri.parse(list.get(0).getAvatar()));
    }

    private void editProfile() {
        try {
            var hinhanhUS = "";
            if (listUri.size() > 0) {
                hinhanhUS = listUri.get(0).toString();
            } else {
                var sharedPreferences = requireContext().getSharedPreferences("INFOR", MODE_PRIVATE);
                var id = sharedPreferences.getString("ID", "");
                var list = dao.getAllUser(id);
                hinhanhUS = list.get(0).getAvatar();
            }

            var name = binding.txtNameUser.getText().toString();
            var position = binding.txtposition.getText().toString();
            var homeTown = binding.txthomeTown.getText().toString();
            var sharedPreferences = requireContext().getSharedPreferences("INFOR", MODE_PRIVATE);
            var id = sharedPreferences.getString("ID", "");

            if (name.isEmpty() || position.isEmpty() || homeTown.isEmpty()) {
                Toast.makeText(requireContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                binding.txtNameUser.setError("Không được để trống");
                binding.txtposition.setError("Không được để trống");
                binding.txthomeTown.setError("Không được để trống");
            } else {
                var check = dao.updateAccount(id, name, position, homeTown, hinhanhUS);
                if (check) {
                    Toast.makeText(requireContext(), "Update thành công", Toast.LENGTH_SHORT).show();
                    var base = new DbHelper(requireContext());
                    base.updateLastAction(id);
                    getParentFragmentManager().popBackStack();
                } else {
                    Toast.makeText(requireContext(), "Update không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void requestPermission() {
        var strings = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(requireContext(), strings)) {
            imagePicker();
        } else {
            EasyPermissions.requestPermissions(this, "Cấp quyền truy cập ảnh", 100, strings);
        }
    }

    public void imagePicker() {
        FilePickerBuilder.getInstance()
                .setActivityTitle("Chọn ảnh")
                .setSpan(FilePickerConst.SPAN_TYPE.FOLDER_SPAN, 3)
                .setSpan(FilePickerConst.SPAN_TYPE.DETAIL_SPAN, 4)
                .setMaxCount(1)
                .setSelectedFiles(listUri)
                .setActivityTheme(R.style.CustomTheme)
                .pickPhoto(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == FilePickerConst.REQUEST_CODE_PHOTO) {
                listUri = data.getParcelableArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA);
                binding.imgUser.setImageURI(listUri.get(0));
            }
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == 100 && perms.size() == 1) {
            imagePicker();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        } else {
            Toast.makeText(requireContext(), "Premission denied", Toast.LENGTH_SHORT).show();
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

    //hàm gọi từ activity để đóng menu
    private void closeMenu() {
        ((MenuController) requireActivity()).closeMenu();
    }
}