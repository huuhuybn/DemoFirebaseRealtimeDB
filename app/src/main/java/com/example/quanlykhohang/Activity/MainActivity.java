package com.example.quanlykhohang.Activity;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.example.quanlykhohang.Fragment.FraThongKe.baoCao;
import com.example.quanlykhohang.Fragment.FraProduct.fragment_product;
import com.example.quanlykhohang.Fragment.FraUser.fragment_user;
import com.example.quanlykhohang.Fragment.FraPhieuNhap.phieuNhap;
import com.example.quanlykhohang.Fragment.FraPhieuXuat.phieuXuat;
import com.example.quanlykhohang.Interface.FragmentInteractionListener;
import com.example.quanlykhohang.Interface.MenuController;
import com.example.quanlykhohang.Interface.TransFerFra;
import com.example.quanlykhohang.R;
import com.example.quanlykhohang.dao.userDAO;
import com.example.quanlykhohang.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements MenuController, FragmentInteractionListener, TransFerFra {
    private ActivityMainBinding binding;
    private fragment_product yourFragment;
    private userDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        var header = binding.nav.getHeaderView(0);
        TextView txtTen = header.findViewById(R.id.txtNamess);
        TextView fullName = header.findViewById(R.id.txtFullNames);
        ImageView imgAvatar = header.findViewById(R.id.imgAvatarr);

        var sharedPreferences = this.getSharedPreferences("INFOR", MODE_PRIVATE);
        var id = sharedPreferences.getString("ID", "");
        dao = new userDAO(this);
        var list = dao.getAllUser(id);
        imgAvatar.setImageURI(Uri.parse(list.get(0).getAvatar()));
        fullName.setText(list.get(0).getName());
        txtTen.setText(list.get(0).getPosition());
        setSupportActionBar(binding.toolbar);
        var actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_24);
        getSupportFragmentManager().beginTransaction().replace(R.id.fame, new phieuNhap()).commit();
        binding.nav.setNavigationItemSelectedListener(item -> {
            Fragment fragment = null;
            var itemId = item.getItemId();
            if (itemId == R.id.product) {
                fragment = new fragment_product();
            } else if (itemId == R.id.user) {
                fragment = new fragment_user();
            }  else if (itemId == R.id.dangxuat) {
                var intent = new Intent(MainActivity.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivities(new Intent[]{intent});
            }
            if (fragment != null) {
                var fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fame, fragment).commit();
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                binding.toolbar.setTitle(item.getTitle());
            }
            return false;
        });


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int itemId = item.getItemId();
            if (itemId == R.id.baocao) {
                fragment = new baoCao();
            } else if (itemId == R.id.phieunhap) {
                fragment = new phieuNhap();
            } else {
                fragment = new phieuXuat();
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fame, fragment)
                    .commit();
            binding.toolbar.setTitle(item.getTitle());
            binding.drawerLayout.close();
            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            binding.drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void closeMenu() {
        binding.drawerLayout.closeDrawer(GravityCompat.START);
    }



    @Override
    public void onFragmentBackPressed() {
        boolean isExpanded = yourFragment.isExpanded();
        // Xử lý sự kiện khi Fragment yêu cầu trở về
        if (isExpanded) {
            yourFragment.shrinkFab();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onFragmentDispatchTouchEvent(MotionEvent ev) {
        // Xử lý sự kiện khi Fragment yêu cầu xử lý sự kiện chạm vào màn hình
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // ...
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void transferFragment(Fragment fragment,String name) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fame, fragment)
                .addToBackStack(name)
                .commit();
    }
    private void updatedUserInterface() {

    }
}

