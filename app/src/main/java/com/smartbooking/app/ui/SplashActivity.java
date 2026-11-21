package com.smartbooking.app.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.smartbooking.app.data.repository.TokenManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TokenManager tokenManager = new TokenManager(this);

        Class<?> destino = tokenManager.getAccess() != null ? MainActivity.class : LoginActivity.class;

        startActivity(new Intent(this, destino));
        finish();
    }
}