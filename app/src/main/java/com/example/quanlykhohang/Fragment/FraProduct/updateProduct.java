package com.example.quanlykhohang.Fragment.FraProduct;

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

import com.example.quanlykhohang.Interface.MenuController;
import com.example.quanlykhohang.R;
import com.example.quanlykhohang.adapter.ProductADT.productAdapter;
import com.example.quanlykhohang.dao.productDAO;
import com.example.quanlykhohang.database.DbHelper;
import com.example.quanlykhohang.databinding.FragmentUpdateProductBinding;
import com.example.quanlykhohang.model.Product;

import java.util.ArrayList;
import java.util.List;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class updateProduct extends Fragment implements EasyPermissions.PermissionCallbacks {
    public static final String TAG = updateProduct.class.getName();
    ArrayList<Uri> listUri = new ArrayList<>();
    Bundle args;
    private productAdapter adapter;

    private FragmentUpdateProductBinding binding;
    private productDAO dao;
    private ArrayList<Product> list;

    public updateProduct() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        var view = inflater.inflate(R.layout.fragment_update_product, container, false);
        binding = FragmentUpdateProductBinding.bind(view);
        list = new ArrayList<>();

        setHasOptionsMenu(true);
        var actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);

        args = getArguments();
        if (args != null) {
            binding.txtGiaSP.setText(args.getString("price"));
            binding.txtTenSP.setText(args.getString("name"));
            binding.txtSoLuong.setText(args.getString("quantity"));
            binding.imgHinhSP.setImageURI(Uri.parse(args.getString("photo")));
        }
        binding.imgHinhSP.setOnClickListener(v -> {
            requestPermission();
        });
        binding.btnThemSP.setOnClickListener(v -> {
            addProducts();
        });
        return view;
    }

    private void addProducts() {
        try {
            var hinhanhSP = "";
            if (listUri.size() > 0) {
                hinhanhSP = listUri.get(0).toString();
            } else {
                hinhanhSP = args.getString("photo");
            }
            var tensp = binding.txtTenSP.getText().toString().trim();
            var giasp = binding.txtGiaSP.getText().toString().trim();
            var soluong = binding.txtSoLuong.getText().toString().trim();
            var sharedPreferences = requireContext().getSharedPreferences("INFOR", MODE_PRIVATE);
            var id = sharedPreferences.getString("ID", "");

            if (tensp.isEmpty() || giasp.isEmpty() || soluong.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    var gia = Integer.parseInt(giasp);
                    var sol = Integer.parseInt(soluong);
                    var pr = new Product(tensp, gia, sol, hinhanhSP, id);
                    dao = new productDAO(requireContext());
                    var check = dao.updateProduct(pr, Integer.parseInt(args.getString("id")));
                    if (check) {
                        var base = new DbHelper(requireContext());
                        base.updateLastAction(id);
                        Toast.makeText(requireContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        getParentFragmentManager().popBackStack();
                    } else {
                        Toast.makeText(requireContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(requireContext(), "Giá và số lượng phải là số", Toast.LENGTH_SHORT).show();
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
                binding.imgHinhSP.setImageURI(listUri.get(0));
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

    private void closeMenu() {
        ((MenuController) requireActivity()).closeMenu();
    }
}