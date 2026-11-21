package com.smartbooking.app.data.api;

import androidx.annotation.NonNull;

import com.smartbooking.app.data.repository.TokenManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private final TokenManager tokenManager;

    public AuthInterceptor(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        String ruta = original.url().encodedPath();

        if (ruta.endsWith("auth/login/")
                || ruta.endsWith("auth/register/")
                || ruta.endsWith("auth/refresh/")) {
            return chain.proceed(original);
        }

        String access = tokenManager.getAccess();
        if (access == null) {
            return chain.proceed(original);
        }

        Request conToken = original.newBuilder()
                .header("Authorization", "Bearer " + access)
                .build();

        return chain.proceed(conToken);
    }
}