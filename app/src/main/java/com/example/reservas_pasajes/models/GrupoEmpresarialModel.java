package com.example.reservas_pasajes.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GrupoEmpresarialModel {

    @SerializedName("grpempId")
    @Expose
    private String grpempId;

    @SerializedName("grpempNombre")
    @Expose
    private String grpempNombre;

    public String getGrpempId() {
        return grpempId;
    }

    public void setGrpempId(String grpempId) {
        this.grpempId = grpempId;
    }

    public String getGrpempNombre() {
        return grpempNombre;
    }

    public void setGrpempNombre(String grpempNombre) {
        this.grpempNombre = grpempNombre;
    }

    @Override
    public String toString() {
        return this.grpempNombre.toUpperCase();
    }
}
