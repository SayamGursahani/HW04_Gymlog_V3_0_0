package com.example.hw04_gymlog_v300;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    public static final String PREFS = "gymlog_prefs";
    public static final String KEY_USER = "logged_user";

    private EditText usernameEt, passwordEt, dummy; // dummy not used; keep simple

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Auto-forward if already logged in
        String already = getSharedPreferences(PREFS, MODE_PRIVATE).getString(KEY_USER, null);
        if (already != null) {
            goToMain();
            return;
        }

        usernameEt = findViewById(R.id.login_username);
        passwordEt = findViewById(R.id.login_password);
        Button loginBtn = findViewById(R.id.login_button);

        loginBtn.setOnClickListener(v -> {
            String u = usernameEt.getText().toString().trim();
            String p = passwordEt.getText().toString().trim();

            if (validateCredentials(u, p)) {
                SharedPreferences sp = getSharedPreferences(PREFS, MODE_PRIVATE);
                sp.edit().putString(KEY_USER, u).apply();
                goToMain();
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goToMain() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    // ---- Pure function used by unit test ----
    public static boolean validateCredentials(String username, String password) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) return false;
        return username.equals("admin1") && password.equals("password1");
    }
}
