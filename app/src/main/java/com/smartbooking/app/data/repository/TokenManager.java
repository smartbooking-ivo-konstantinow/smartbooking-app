package com.smartbooking.app.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

public class TokenManager {

    private static final String ARCHIVO = "smartbooking_tokens";
    private static final String CLAVE_ACCESS = "access";
    private static final String CLAVE_REFRESH = "refresh";

    private final SharedPreferences prefs;

    public TokenManager(Context context) {
        try {
            MasterKey masterKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            prefs = EncryptedSharedPreferences.create(
                    context,
                    ARCHIVO,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo inicializar el almacenamiento seguro", e);
        }
    }

    public void guardarTokens(String access, String refresh) {
        prefs.edit()
                .putString(CLAVE_ACCESS, access)
                .putString(CLAVE_REFRESH, refresh)
                .apply();
    }

    public String getAccess() {
        return prefs.getString(CLAVE_ACCESS, null);
    }

    public String getRefresh() {
        return prefs.getString(CLAVE_REFRESH, null);
    }

    public void borrarTokens() {
        prefs.edit().clear().apply();
    }
}