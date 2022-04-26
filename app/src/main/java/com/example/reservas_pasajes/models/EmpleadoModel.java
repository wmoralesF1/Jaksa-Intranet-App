package com.example.reservas_pasajes.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmpleadoModel {

    @SerializedName("empId")
    @Expose
    private int empId;

    @SerializedName("empApellidos")
    @Expose
    private String empApellidos;

    @SerializedName("empNombre")
    @Expose
    private String empNombre;

    @SerializedName("empTipoDocumentoId")
    @Expose
    private int empTipoDocumentoId;

    @SerializedName("empNumeroDocumento")
    @Expose
    private String empNumeroDocumento;

    @SerializedName("empEstadoCivil")
    @Expose
    private String empEstadoCivil;

    @SerializedName("empFechaNacimiento")
    @Expose
    private String empFechaNacimiento;

    @SerializedName("empNacionalidad")
    @Expose
    private String empNacionalidad ;

    @SerializedName("empCargoId")
    @Expose
    private int empCargoId;

    @SerializedName("empCargoNombre")
    @Expose
    private String empCargoNombre;

    @SerializedName("empLicenciaCategoriaId")
    @Expose
    private String empLicenciaCategoriaId;

    @SerializedName("empNumeroLicencia")
    @Expose
    private String empNumeroLicencia;

    @SerializedName("empEstado")
    @Expose
    private String empEstado ;

    @SerializedName("empTipoServicioId")
    @Expose
    private String empTipoServicioId;

    @SerializedName("empGrpEmpresarialId")
    @Expose
    private String empGrpEmpresarialId;

    @SerializedName("empLocalId")
    @Expose
    private int empLocalId ;

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

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

    public int getEmpTipoDocumentoId() {
        return empTipoDocumentoId;
    }

    public void setEmpTipoDocumentoId(int empTipoDocumentoId) {
        this.empTipoDocumentoId = empTipoDocumentoId;
    }

    public String getEmpNumeroDocumento() {
        return empNumeroDocumento;
    }

    public void setEmpNumeroDocumento(String empNumeroDocumento) {
        this.empNumeroDocumento = empNumeroDocumento;
    }

    public String getEmpEstadoCivil() {
        return empEstadoCivil;
    }

    public void setEmpEstadoCivil(String empEstadoCivil) {
        this.empEstadoCivil = empEstadoCivil;
    }

    public String getEmpFechaNacimiento() {
        return empFechaNacimiento;
    }

    public void setEmpFechaNacimiento(String empFechaNacimiento) {
        this.empFechaNacimiento = empFechaNacimiento;
    }

    public String getEmpNacionalidad() {
        return empNacionalidad;
    }

    public void setEmpNacionalidad(String empNacionalidad) {
        this.empNacionalidad = empNacionalidad;
    }

    public int getEmpCargoId() {
        return empCargoId;
    }

    public void setEmpCargoId(int empCargoId) {
        this.empCargoId = empCargoId;
    }

    public String getEmpCargoNombre() {
        return empCargoNombre;
    }

    public void setEmpCargoNombre(String empCargoNombre) {
        this.empCargoNombre = empCargoNombre;
    }

    public String getEmpLicenciaCategoriaId() {
        return empLicenciaCategoriaId;
    }

    public void setEmpLicenciaCategoriaId(String empLicenciaCategoriaId) {
        this.empLicenciaCategoriaId = empLicenciaCategoriaId;
    }

    public String getEmpNumeroLicencia() {
        return empNumeroLicencia;
    }

    public void setEmpNumeroLicencia(String empNumeroLicencia) {
        this.empNumeroLicencia = empNumeroLicencia;
    }

    public String getEmpEstado() {
        return empEstado;
    }

    public void setEmpEstado(String empEstado) {
        this.empEstado = empEstado;
    }

    public String getEmpTipoServicioId() {
        return empTipoServicioId;
    }

    public void setEmpTipoServicioId(String empTipoServicioId) {
        this.empTipoServicioId = empTipoServicioId;
    }

    public String getEmpGrpEmpresarialId() {
        return empGrpEmpresarialId;
    }

    public void setEmpGrpEmpresarialId(String empGrpEmpresarialId) {
        this.empGrpEmpresarialId = empGrpEmpresarialId;
    }

    public int getEmpLocalId() {
        return empLocalId;
    }

    public void setEmpLocalId(int empLocalId) {
        this.empLocalId = empLocalId;
    }
}
