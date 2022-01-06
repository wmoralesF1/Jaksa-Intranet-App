package com.example.reservas_pasajes.models;

public class AsientoModel {
    public int getNroAsiento() {
        return NroAsiento;
    }

    public void setNroAsiento(int nroAsiento) {
        NroAsiento = nroAsiento;
    }

    public AsientoModel() {}

    public AsientoModel(int nroAsiento) {
        NroAsiento = nroAsiento;
    }

    private int NroAsiento;

}
