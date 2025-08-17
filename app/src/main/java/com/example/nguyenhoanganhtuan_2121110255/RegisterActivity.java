package com.example.nguyenhoanganhtuan_2121110255;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText txtUsername, txtemail, txtPass;
    Button btnRegister;
    TextView txtLogin;
    private final String API_URL = "https://6895908c039a1a2b288f7f07.mockapi.io/users"; // Replace with your actual API URL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtUsername = findViewById(R.id.txtUsername);
        txtemail = findViewById(R.id.txtemail);
        txtPass = findViewById(R.id.txtPass);
        btnRegister = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);

        txtLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });

        btnRegister.setOnClickListener(v -> {
            String user = txtUsername.getText().toString().trim();
            String email = txtemail.getText().toString().trim();
            String pass = txtPass.getText().toString().trim();

            if (user.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }


            StringRequest request = new StringRequest(Request.Method.POST, API_URL,
                    response -> {
                        try {
                            JSONObject obj = new JSONObject(response);

                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công! Hãy đăng nhập", Toast.LENGTH_SHORT).show();
                                // 👉 Sau khi đăng ký thành công thì quay lại Login
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(RegisterActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show()
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", user);
                    params.put("email", email);
                    params.put("password", pass);
                    return params;
                }
            };

            Volley.newRequestQueue(this).add(request);
        });
    }
}