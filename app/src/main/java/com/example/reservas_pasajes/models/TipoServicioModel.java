package com.example.reservas_pasajes.models;

public class TipoServicioModel {
    private String idServicio;
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
        return this.nomServicio;
    }
}
