package com.example.reservas_pasajes.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaisModel {

    @SerializedName("paisNombre")
    @Expose
    private String paisNombre;

    public String getPaisNombre() {
        return paisNombre;
    }

    public void setPaisNombre(String paisNombre) {
        this.paisNombre = paisNombre;
    }

    @Override
    public String toString() {
        return this.paisNombre.toUpperCase();
    }
}
