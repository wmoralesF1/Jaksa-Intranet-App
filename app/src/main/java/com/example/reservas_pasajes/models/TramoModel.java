package com.example.reservas_pasajes.models;

public class TramoModel {

    private String idTramo;
    private String nomTramo;

    public String getIdTramo() {
        return idTramo;
    }

    public void setIdTramo(String idTramo) {
        this.idTramo = idTramo;
    }

    public String getNomTramo() {
        return nomTramo;
    }

    public void setNomTramo(String nomTramo) {
        this.nomTramo = nomTramo;
    }

    @Override
    public String toString() {
        return this.nomTramo;
    }

}
