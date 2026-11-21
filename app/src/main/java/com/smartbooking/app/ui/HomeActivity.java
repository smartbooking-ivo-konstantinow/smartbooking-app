package com.smartbooking.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.smartbooking.app.R;
import com.smartbooking.app.data.api.ApiClient;
import com.smartbooking.app.data.api.ApiService;
import com.smartbooking.app.data.model.PerfilResponse;
import com.smartbooking.app.data.repository.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private TextView tvSaludo;
    private ProgressBar progress;
    private View menu;
    private Button btnReservas, btnUsuarios, btnPerfil, btnSala, btnContacto, btnCerrarSesion;

    private TokenManager tokenManager;
    private ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvSaludo = findViewById(R.id.tvSaludo);
        progress = findViewById(R.id.progress);
        menu = findViewById(R.id.menu);
        btnReservas = findViewById(R.id.btnReservas);
        btnUsuarios = findViewById(R.id.btnUsuarios);
        btnPerfil = findViewById(R.id.btnPerfil);
        btnSala = findViewById(R.id.btnSala);
        btnContacto = findViewById(R.id.btnContacto);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        tokenManager = new TokenManager(this);
        api = ApiClient.getClient(this).create(ApiService.class);

        btnCerrarSesion.setOnClickListener(v -> cerrarSesion());

        View.OnClickListener proximamente = v ->
                Toast.makeText(this, "Disponible en el próximo sprint", Toast.LENGTH_SHORT).show();
        btnReservas.setOnClickListener(proximamente);
        btnUsuarios.setOnClickListener(proximamente);
        btnPerfil.setOnClickListener(proximamente);
        btnSala.setOnClickListener(proximamente);
        btnContacto.setOnClickListener(proximamente);

        cargarPerfil();
    }

    private void cargarPerfil() {
        progress.setVisibility(View.VISIBLE);

        api.getPerfil().enqueue(new Callback<PerfilResponse>() {
            @Override
            public void onResponse(Call<PerfilResponse> call, Response<PerfilResponse> response) {
                progress.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    mostrarMenu(response.body());
                } else if (response.code() == 401) {
                    tokenManager.borrarTokens();
                    irALogin();
                } else {
                    Toast.makeText(HomeActivity.this,
                            "Error del servidor. Intentá más tarde.",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PerfilResponse> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(HomeActivity.this,
                        "No se pudo conectar con el servidor",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void mostrarMenu(PerfilResponse perfil) {
        tvSaludo.setText(getString(R.string.home_saludo, perfil.getFirstName()));

        boolean esAdmin = "ADMIN".equals(perfil.getRol());
        btnReservas.setText(esAdmin ? R.string.home_reservas_admin : R.string.home_reservas_usuario);
        btnUsuarios.setVisibility(esAdmin ? View.VISIBLE : View.GONE);

        menu.setVisibility(View.VISIBLE);
    }

    private void cerrarSesion() {
        tokenManager.borrarTokens();
        irALogin();
    }

    private void irALogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}