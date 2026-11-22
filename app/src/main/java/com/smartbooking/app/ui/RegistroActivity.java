package com.smartbooking.app.ui;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smartbooking.app.R;
import com.smartbooking.app.data.api.ApiClient;
import com.smartbooking.app.data.api.ApiService;
import com.smartbooking.app.data.model.RegistroRequest;
import com.smartbooking.app.utils.PasswordValidator;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    private TextInputLayout tilNombre, tilApellido, tilEmail, tilPassword, tilConfirmar;
    private TextInputEditText etNombre, etApellido, etEmail, etPassword, etConfirmar;
    private Button btnRegistrar;
    private ProgressBar progress;

    private ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        tilNombre = findViewById(R.id.tilNombre);
        tilApellido = findViewById(R.id.tilApellido);
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        tilConfirmar = findViewById(R.id.tilConfirmar);
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmar = findViewById(R.id.etConfirmar);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        progress = findViewById(R.id.progress);

        api = ApiClient.getClient(this).create(ApiService.class);
        btnRegistrar.setOnClickListener(v -> intentarRegistro());
    }

    private void intentarRegistro() {
        limpiarErrores();

        String nombre = etNombre.getText().toString().trim();
        String apellido = etApellido.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirmar = etConfirmar.getText().toString();

        if (nombre.isEmpty()) {
            tilNombre.setError("Ingresá tu nombre");
            return;
        }
        if (apellido.isEmpty()) {
            tilApellido.setError("Ingresá tu apellido");
            return;
        }
        if (email.isEmpty()) {
            tilEmail.setError("Ingresá tu email");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Ingresá un email válido");
            return;
        }
        String errorPassword = PasswordValidator.validar(password);
        if (errorPassword != null) {
            tilPassword.setError(errorPassword);
            return;
        }
        if (!password.equals(confirmar)) {
            tilConfirmar.setError("Las contraseñas no coinciden");
            return;
        }

        setCargando(true);

        api.registrar(new RegistroRequest(email, password, nombre, apellido))
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        setCargando(false);
                        if (response.code() == 201) {
                            Toast.makeText(RegistroActivity.this,
                                    "Cuenta creada. Ya podés iniciar sesión.",
                                    Toast.LENGTH_LONG).show();
                            finish();
                        } else if (response.code() == 400) {
                            mostrarErroresDelServidor(response.errorBody());
                        } else {
                            Toast.makeText(RegistroActivity.this,
                                    "Error del servidor. Intentá más tarde.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        setCargando(false);
                        Toast.makeText(RegistroActivity.this,
                                "No se pudo conectar con el servidor",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void mostrarErroresDelServidor(ResponseBody errorBody) {
        try {
            Type tipo = new TypeToken<Map<String, List<String>>>() {}.getType();
            Map<String, List<String>> errores = new Gson().fromJson(errorBody.charStream(), tipo);

            if (errores.containsKey("email")) tilEmail.setError(errores.get("email").get(0));
            if (errores.containsKey("password")) tilPassword.setError(errores.get("password").get(0));
            if (errores.containsKey("first_name")) tilNombre.setError(errores.get("first_name").get(0));
            if (errores.containsKey("last_name")) tilApellido.setError(errores.get("last_name").get(0));
        } catch (Exception e) {
            Toast.makeText(this, "Revisá los datos ingresados.", Toast.LENGTH_LONG).show();
        }
    }

    private void limpiarErrores() {
        tilNombre.setError(null);
        tilApellido.setError(null);
        tilEmail.setError(null);
        tilPassword.setError(null);
        tilConfirmar.setError(null);
    }

    private void setCargando(boolean cargando) {
        progress.setVisibility(cargando ? View.VISIBLE : View.GONE);
        btnRegistrar.setEnabled(!cargando);
    }
}