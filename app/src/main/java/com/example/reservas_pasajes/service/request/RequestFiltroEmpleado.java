package com.example.reservas_pasajes.service.request;

import com.google.gson.annotations.SerializedName;

public class RequestFiltroEmpleado {
    @SerializedName("empApellidos")
    private String empApellidos = "";

    @SerializedName("empNombre")
    private String empNombre = "";

    @SerializedName("empNumeroDocumento")
    private String empNumeroDocumento = "";

    public String getEmpApellidos() {
        return empApellidos;
    }

    public void setEmpApellidos(String empApellidos) {
        this.empApellidos = empApellidos;
    }

    public String getEmpNombre() {
        return empNombre;
    }

    public void setEmpNombre(String empNombre) {
        this.empNombre = empNombre;
    }

    public String getEmpNumeroDocumento() {
        return empNumeroDocumento;
    }

    public void setEmpNumeroDocumento(String empNumeroDocumento) {
        this.empNumeroDocumento = empNumeroDocumento;
    }
}
