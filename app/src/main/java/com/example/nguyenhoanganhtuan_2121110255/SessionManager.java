package com.example.nguyenhoanganhtuan_2121110255;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context context;

    public SessionManager(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveUser(String username, String token) {
        editor.putString("USERNAME", username);
        editor.putString("TOKEN", token);
        editor.apply();
    }

    public String getUsername() {
        return prefs.getString("USERNAME", null);
    }

    public String getToken() {
        return prefs.getString("TOKEN", null);
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}
