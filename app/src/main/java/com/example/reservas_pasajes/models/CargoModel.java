package com.example.reservas_pasajes.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CargoModel {

    @SerializedName("cargoId")
    @Expose
    private int cargoId;

    @SerializedName("cargoNombre")
    @Expose
    private String cargoNombre;

    public int getCargoId() {
        return cargoId;
    }

    public void setCargoId(int cargoId) {
        this.cargoId = cargoId;
    }

    public String getCargoNombre() {
        return cargoNombre;
    }

    public void setCargoNombre(String cargoNombre) {
        this.cargoNombre = cargoNombre;
    }

    @Override
    public String toString() {
        return this.cargoNombre.toUpperCase();
    }
}
