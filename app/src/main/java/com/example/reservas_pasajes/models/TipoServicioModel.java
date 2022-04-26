package com.example.reservas_pasajes.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TipoServicioModel {


    @SerializedName("tipoServicioId")
    @Expose
    private String idServicio;

    @SerializedName("tipoServicioNombre")
    @Expose
    private String nomServicio;

    public String getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    public String getNomServicio() {
        return nomServicio;
    }

    public void setNomServicio(String nomServicio) {
        this.nomServicio = nomServicio;
    }

    @Override
    public String toString() {
        return this.nomServicio.toUpperCase();
    }
}
