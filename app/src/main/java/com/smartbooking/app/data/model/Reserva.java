package com.smartbooking.app.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Reserva implements Serializable {

    private int id;
    private int usuario;
    private String fecha;

    @SerializedName("hora_inicio")
    private String horaInicio;

    @SerializedName("hora_fin")
    private String horaFin;

    private String motivo;

    public int getId() { return id; }
    public int getUsuario() { return usuario; }
    public String getFecha() { return fecha; }
    public String getHoraInicio() { return horaInicio; }
    public String getHoraFin() { return horaFin; }
    public String getMotivo() { return motivo; }
}