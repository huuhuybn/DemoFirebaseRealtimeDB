package com.example.quanlykhohang.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlykhohang.dao.userDAO;
import com.example.quanlykhohang.database.DbHelper;
import com.example.quanlykhohang.databinding.ActivityLoginBinding;

public class Login extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        var userDAO = new userDAO(this);
        var sharedPreferences = getSharedPreferences("INFOR", MODE_PRIVATE);
        var rememberPassword = sharedPreferences.getBoolean("SAVE_PASSWORD", false);
        binding.checkBox.setChecked(rememberPassword);
        if (rememberPassword) {
            // Nếu Checkbox đã được chọn, thì khôi phục tài khoản và mật khẩu
            var savedUsername = sharedPreferences.getString("ID", "");
            var savedPassword = sharedPreferences.getString("PASSWORD", "");
            binding.edtUsername.setText(savedUsername);
            binding.edtPassword.setText(savedPassword);
        }

        binding.btnDangNhap.setOnClickListener(view -> {
            var username = binding.edtUsername.getText().toString();
            var password = binding.edtPassword.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                binding.edtUsername.setError("Please enter username");
                binding.edtPassword.setError("Please enter password");
                Toast.makeText(this, "Please enter complete login information", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    if (userDAO.checkUser(username, password)) {
                        var intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        if (binding.checkBox.isChecked()) {
                            var editor = sharedPreferences.edit();
                            editor.putString("ID", username);
                            editor.putString("PASSWORD", password);
                            editor.putBoolean("SAVE_PASSWORD", true);
                            editor.apply();
                        } else {
                            // Nếu Checkbox không được chọn, xóa tài khoản và mật khẩu đã lưu
                            var editor = sharedPreferences.edit();
                            editor.remove("SAVE_PASSWORD");
                            editor.remove("PASSWORD");

                            editor.putBoolean("SAVE_PASSWORD", false);
                            editor.apply();
                        }
                        var id = sharedPreferences.getString("ID", "");
                        var base = new DbHelper(this);
                        base.updateLasLogin(id);
                        Toast.makeText(this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        binding.edtUsername.setError("Username or password is incorrect");
                        binding.edtPassword.setError("Username or password is incorrect");
                        Toast.makeText(this, "Username or password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("Login error", "ERROR: " + e.getMessage());
                    Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}