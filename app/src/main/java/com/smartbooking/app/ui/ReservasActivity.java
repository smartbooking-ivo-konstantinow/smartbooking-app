
package com.smartbooking.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smartbooking.app.R;
import com.smartbooking.app.data.api.ApiClient;
import com.smartbooking.app.data.api.ApiService;
import com.smartbooking.app.data.model.Reserva;
import com.smartbooking.app.data.repository.TokenManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservasActivity extends AppCompatActivity {

    private RecyclerView rvReservas;
    private ProgressBar progress;
    private TextView tvVacia;
    private View layoutError;

    private ReservasAdapter adapter;
    private ApiService api;
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);

        rvReservas = findViewById(R.id.rvReservas);
        progress = findViewById(R.id.progress);
        tvVacia = findViewById(R.id.tvVacia);
        layoutError = findViewById(R.id.layoutError);

        Button btnReintentar = findViewById(R.id.btnReintentar);

        adapter = new ReservasAdapter(reserva -> {
            // Aquí se abrirá el detalle de la reserva.
        });

        rvReservas.setLayoutManager(new LinearLayoutManager(this));
        rvReservas.setAdapter(adapter);

        api = ApiClient.getClient(this).create(ApiService.class);
        tokenManager = new TokenManager(this);

        btnReintentar.setOnClickListener(v -> cargarReservas());

        cargarReservas();
    }

    private void cargarReservas() {

        mostrarSolo(progress);

        api.getReservas().enqueue(new Callback<List<Reserva>>() {

            @Override
            public void onResponse(
                    Call<List<Reserva>> call,
                    Response<List<Reserva>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    List<Reserva> reservas = response.body();

                    if (reservas.isEmpty()) {
                        mostrarSolo(tvVacia);
                    } else {
                        adapter.setReservas(reservas);
                        mostrarSolo(rvReservas);
                    }

                } else if (response.code() == 401) {

                    tokenManager.borrarTokens();
                    irALogin();

                } else {

                    mostrarSolo(layoutError);
                }
            }

            @Override
            public void onFailure(
                    Call<List<Reserva>> call,
                    Throwable t) {

                mostrarSolo(layoutError);
            }
        });
    }

    private void mostrarSolo(View visible) {

        rvReservas.setVisibility(
                visible == rvReservas ? View.VISIBLE : View.GONE);

        progress.setVisibility(
                visible == progress ? View.VISIBLE : View.GONE);

        tvVacia.setVisibility(
                visible == tvVacia ? View.VISIBLE : View.GONE);

        layoutError.setVisibility(
                visible == layoutError ? View.VISIBLE : View.GONE);
    }

    private void irALogin() {

        Intent intent = new Intent(this, LoginActivity.class);

        intent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        finish();
    }
}
