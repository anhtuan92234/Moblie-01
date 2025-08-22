package com.example.nguyenhoanganhtuan_2121110255;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "USER_SESSION";
    private static final String KEY_USER_ID = "USER_ID";
    private static final String KEY_USERNAME = "USERNAME";
    private static final String KEY_EMAIL = "EMAIL";
    private static final String KEY_PASSWORD = "PASSWORD";
    private static final String KEY_TOKEN = "TOKEN";

    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        prefs  = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    // ✅ Đầy đủ thông tin (khuyến nghị dùng)
    public void saveUser(String userId, String username, String email, String password, String token) {
        editor.putString(KEY_USER_ID, userId);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    // 🔁 Tương thích ngược: bản cũ 2 tham số (username, token)
    // Không đủ email/password/id -> chỉ dùng tạm để khỏi lỗi biên dịch.
    public void saveUser(String username, String token) {
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    // Cập nhật sau khi user chỉnh sửa Account
    public void updateUser(String username, String email, String password) {
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
    }

    public String getUserId()   { return prefs.getString(KEY_USER_ID, null); }
    public String getUsername() { return prefs.getString(KEY_USERNAME, null); }
    public String getEmail()    { return prefs.getString(KEY_EMAIL, null); }
    public String getPassword() { return prefs.getString(KEY_PASSWORD, null); }
    public String getToken()    { return prefs.getString(KEY_TOKEN, null); }

    public boolean isLoggedIn() {
        // Tuỳ bạn: check theo TOKEN hoặc USER_ID
        return getUserId() != null || getToken() != null;
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}
