package com.example.quanlykhohang.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlykhohang.database.DbHelper;
import com.example.quanlykhohang.model.User;

import java.util.ArrayList;

public class userDAO {
    SharedPreferences sharedPreferences;
    private DbHelper base;

    public userDAO(Context context) {
        base = new DbHelper(context);
        sharedPreferences = context.getSharedPreferences("INFOR", Context.MODE_PRIVATE);
    }

    public ArrayList<User> getAllUser(String username) {
        var list = new ArrayList<User>();
        var cursor = base.getReadableDatabase().rawQuery("SELECT US.NAME,US.HOMETOWN,US.IMAGE,US.POSITION FROM USER US WHERE US.ID = ?", new String[]{username});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(new User(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                ));
            }
            cursor.close();
        }
        return list;
    }

    public ArrayList<User> getAllListUser() {
        var list = new ArrayList<User>();
        var cursor = base.getReadableDatabase().rawQuery("SELECT US.ID,US.NAME,US.HOMETOWN,US.IMAGE,US.POSITION,US.LASTLOGIN,US.LASTACTION FROM USER US", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(new User(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                ));
            }
            cursor.close();
        }
        return list;
    }

    public boolean checkUser(String username, String password) {
        var sqLiteDatabase = base.getReadableDatabase();
        var sql = "SELECT * FROM USER WHERE ID=? AND PASSWORD=?";
        try (var cursor = sqLiteDatabase.rawQuery(sql, new String[]{username, password})) {
            cursor.moveToFirst();
            var editor = sharedPreferences.edit();
            editor.putString("ID", cursor.getString(0));
            editor.putString("NAME", cursor.getString(1));
            editor.putString("POSITION", cursor.getString(3));
            editor.commit();
            return cursor.getCount() != 0;
        }
    }

    public int changePassword(String username, String oldPassword, String newPassword) {
        SQLiteDatabase sqLiteDatabase = base.getWritableDatabase();
        String sql = "SELECT * FROM USER WHERE ID=? AND PASSWORD=?";
        try (Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{username, oldPassword})) {
            if (cursor.getCount() > 0) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("PASSWORD", newPassword);

                String whereClause = "ID = ? AND PASSWORD = ?";
                String[] whereArgs = {username, oldPassword};

                int updatedRows = base.getWritableDatabase().update("USER", contentValues, whereClause, whereArgs);

                if (updatedRows > 0) {
                    // Đổi mật khẩu thành công
                    return 1;
                } else if (updatedRows == 0) {
                    // Không có dòng nào bị ảnh hưởng (không thay đổi mật khẩu)
                    return 0;
                } else {
                    // Sai tên đăng nhập hoặc mật khẩu cũ
                    return -1;
                }
            } else {
                // Sai tên đăng nhập hoặc mật khẩu cũ
                return -1;
            }
        }
    }

    public boolean createAccount(String username, String fullName, String password, String position, String lastLogin, String createDate, String lastAction,String hometown,String image) {
        SQLiteDatabase sqLiteDatabase = base.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", username);
        contentValues.put("NAME", fullName);
        contentValues.put("PASSWORD", password);
        contentValues.put("POSITION", position);
        contentValues.put("LASTLOGIN", lastLogin);
        contentValues.put("CREATEDATE", createDate);
        contentValues.put("LASTACTION", lastAction);
        contentValues.put("HOMETOWN", hometown);
        contentValues.put("IMAGE", image);
        long result = sqLiteDatabase.insert("USER", null, contentValues);
        return result > 0;
    }

    public boolean updateAccount( String username,String fullName, String position,String hometown,String image) {
        SQLiteDatabase sqLiteDatabase = base.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", fullName);
        contentValues.put("POSITION", position);
        contentValues.put("HOMETOWN", hometown);
        contentValues.put("IMAGE", image);
        var whereClause = "ID = ?";
        var whereArgs = new String[]{username};
        int updatedRows = sqLiteDatabase.update("USER", contentValues, whereClause, whereArgs);
        return updatedRows > 0;
    }

}
