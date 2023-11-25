package com.example.quanlykhohang.Fragment.FraPhieuXuat;

import static android.content.Context.MODE_PRIVATE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.quanlykhohang.Fragment.FraThongKe.thongkechitietngay;
import com.example.quanlykhohang.Interface.MenuController;
import com.example.quanlykhohang.R;
import com.example.quanlykhohang.dao.billDAO;
import com.example.quanlykhohang.dao.billDetailDAO;
import com.example.quanlykhohang.dao.productDAO;
import com.example.quanlykhohang.database.DbHelper;
import com.example.quanlykhohang.databinding.FragmentAddPhieuXuatBinding;
import com.example.quanlykhohang.model.Bill;
import com.example.quanlykhohang.model.BillDetail;
import com.example.quanlykhohang.model.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AddPhieuXuat extends Fragment {
    public static final String TAG = AddPhieuXuat.class.getName();
    private FragmentAddPhieuXuatBinding binding;

    private billDAO dao;
    private productDAO prod;
    private billDetailDAO detail;

    public AddPhieuXuat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        var view = inflater.inflate(R.layout.fragment_add_phieu_xuat, container, false);
        binding = FragmentAddPhieuXuatBinding.bind(view);
        setHasOptionsMenu(true);
        var actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        getdataProduct(binding.spnTenSP);
        final int[] index = {0};
        binding.seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                index[0] = i;
                if (index[0] > 1) {
                    binding.btnAdd.setText("Tiếp");
                } else {
                    binding.btnAdd.setText("Tạo");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.btnAdd.setOnClickListener(view1 -> {
            if (index[0] > 1) {
                insertSp();
                index[0] = index[0] - 1;
                binding.spnTenSP.setSelection(0);
                binding.txtSoLuong.setText("");
            } else if (index[0] == 1) {
                insertSp();
                insetHoaDon();
                binding.btnAdd.setText("Tạo");
                getParentFragmentManager().popBackStack();
            } else {
                Toast.makeText(requireContext(), "Vui lòng chọn số sản phẩm muốn thêm lớn hơn 0", Toast.LENGTH_SHORT).show();
            }
        });
        binding.spnTenSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Lấy giá trị đã chọn từ Spinner
                var HMMSP = (HashMap<String, Object>) binding.spnTenSP.getSelectedItem();
                var itt = (int) HMMSP.get("soLuong");
                binding.txtSoLuongConLai.setText(itt + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Xử lý khi không có mục nào được chọn
            }
        });


        return view;
    }

    private void insertSp() {
        var HMMSP = (HashMap<String, Object>) binding.spnTenSP.getSelectedItem();
        var maSP = (int) HMMSP.get("maSP");
        var itt = (int) HMMSP.get("soLuong");
        var gia = (int) HMMSP.get("gia");
        var giaxuat = binding.txtGiaXuat.getText().toString().trim();
        var soL = binding.txtSoLuong.getText().toString().trim();

        if (soL.isEmpty() || giaxuat.isEmpty()) {
            binding.txtSoLuong.setError("Vui lòng nhập số lượng");
            binding.txtGiaXuat.setError("Vui lòng nhập giá xuất");
            return;
        }

        try {
            var soLuongSP = Integer.parseInt(soL);
            var giaXuat = Integer.parseInt(giaxuat);

            if (soLuongSP > itt) {
                Toast.makeText(requireContext(), "Số lượng xuất không được lớn hơn số lượng tồn", Toast.LENGTH_SHORT).show();
                return;
            }

            prod = new productDAO(requireContext());
            var tong = itt - soLuongSP;
            prod.updateProductBill(tong, maSP);

            dao = new billDAO(requireContext());
            var idbill = dao.getLatestBillId();

            detail = new billDetailDAO(requireContext());
            var createdate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

            var de = new BillDetail(maSP, idbill + 1, soLuongSP + "", gia + "", giaXuat, createdate);
            var check = detail.addBillDetail(de);

            if (check) {
                var sharedPreferences = requireContext().getSharedPreferences("INFOR", MODE_PRIVATE);
                var id = sharedPreferences.getString("ID", "");
                var base = new DbHelper(requireContext());
                base.updateLastAction(id);
                Toast.makeText(requireContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(requireContext(), "Số lượng và giá xuất phải là số", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    private void getdataProduct(Spinner spnTenSP) {
        var prod = new productDAO(requireContext());
        var sanpham = prod.getALlProduct();
        var listHashMap = new ArrayList<HashMap<String, Object>>();
        for (Product i : sanpham) {
            var hashMap = new HashMap<String, Object>();
            hashMap.put("maSP", i.getId());
            hashMap.put("nameSP", i.getName());
            hashMap.put("gia", i.getPrice());
            hashMap.put("soLuong", i.getQuantity());
            listHashMap.add(hashMap);
        }
        var simpleAdapter = new SimpleAdapter(requireContext(), listHashMap, android.R.layout.simple_list_item_1, new String[]{"nameSP"}, new int[]{android.R.id.text1});
        spnTenSP.setAdapter(simpleAdapter);
    }


    private void insetHoaDon() {
        var sharedPreferences = requireContext().getSharedPreferences("INFOR", MODE_PRIVATE);
        var id = sharedPreferences.getString("ID", "");
        var sdfDate = new SimpleDateFormat("dd-MM-yyyy");
        var currentDate = sdfDate.format(new Date());
        var createdate = currentDate;
        dao = new billDAO(requireContext());
        var bi = new Bill(id, "1", createdate, "");
        dao.insertBill(bi);
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