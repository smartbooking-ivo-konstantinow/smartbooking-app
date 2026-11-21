package com.smartbooking.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.smartbooking.app.R;
import com.smartbooking.app.data.api.ApiClient;
import com.smartbooking.app.data.api.ApiService;
import com.smartbooking.app.data.model.LoginRequest;
import com.smartbooking.app.data.model.LoginResponse;
import com.smartbooking.app.data.repository.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout tilEmail, tilPassword;
    private TextInputEditText etEmail, etPassword;
    private Button btnIngresar;
    private ProgressBar progress;

    private TokenManager tokenManager;
    private ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnIngresar = findViewById(R.id.btnIngresar);
        progress = findViewById(R.id.progress);

        tokenManager = new TokenManager(this);
        api = ApiClient.getClient(this).create(ApiService.class);

        btnIngresar.setOnClickListener(v -> intentarLogin());
    }

    private void intentarLogin() {
        tilEmail.setError(null);
        tilPassword.setError(null);

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();

        if (email.isEmpty()) {
            tilEmail.setError("Ingresá tu email");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Ingresá un email válido");
            return;
        }

        if (password.isEmpty()) {
            tilPassword.setError("Ingresá tu contraseña");
            return;
        }

        setCargando(true);

        api.login(new LoginRequest(email, password)).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                setCargando(false);
                if (response.isSuccessful() && response.body() != null) {
                    tokenManager.guardarTokens(
                            response.body().getAccess(),
                            response.body().getRefresh());
                    irAPantallaPrincipal();
                } else if (response.code() == 401) {
                    tilPassword.setError("Email o contraseña incorrectos");
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Error del servidor. Intentá más tarde.",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                setCargando(false);
                Toast.makeText(LoginActivity.this,
                        "No se pudo conectar con el servidor",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setCargando(boolean cargando) {
        progress.setVisibility(cargando ? View.VISIBLE : View.GONE);
        btnIngresar.setEnabled(!cargando);
    }

    private void irAPantallaPrincipal() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}