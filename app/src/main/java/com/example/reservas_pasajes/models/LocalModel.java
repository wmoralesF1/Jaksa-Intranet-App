package com.example.reservas_pasajes.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocalModel {

    @SerializedName("localId")
    @Expose
    private int localId;

    @SerializedName("localNombre")
    @Expose
    private String localNombre;

    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public String getLocalNombre() {
        return localNombre;
    }

    public void setLocalNombre(String localNombre) {
        this.localNombre = localNombre;
    }

    @Override
    public String toString() {
        return this.localNombre.toUpperCase();
    }
}
