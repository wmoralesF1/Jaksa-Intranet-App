package com.example.reservas_pasajes.models;

public class UnidadModel {

    private int idUnidad;
    private String nroUnidad;

    public UnidadModel(){}

    public UnidadModel(int idUnidad, String nroUnidad) {
        this.idUnidad = idUnidad;
        this.nroUnidad = nroUnidad;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getNroUnidad() {
        return nroUnidad;
    }

    public void setNroUnidad(String nroUnidad) {
        this.nroUnidad = nroUnidad;
    }

    @Override
    public String toString() {
        return this.nroUnidad.toUpperCase();
    }
}
