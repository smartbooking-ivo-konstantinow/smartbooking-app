package com.smartbooking.app.data.api;

import com.smartbooking.app.data.model.LoginRequest;
import com.smartbooking.app.data.model.LoginResponse;
import com.smartbooking.app.data.model.LogoutRequest;
import com.smartbooking.app.data.model.PerfilResponse;
import com.smartbooking.app.data.model.RegistroRequest;
import com.smartbooking.app.data.model.Reserva;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;

public interface ApiService {

    @POST("auth/login/")
    Call<LoginResponse> login(@Body LoginRequest request);
    @GET("perfil/")
    Call<PerfilResponse> getPerfil();

    @POST("auth/logout/")
    Call<Void> logout(@Body LogoutRequest request);

    @POST("auth/register/")
    Call<Void> registrar(@Body RegistroRequest request);

    @GET("reservas/")
    Call<List<Reserva>> getReservas();
}