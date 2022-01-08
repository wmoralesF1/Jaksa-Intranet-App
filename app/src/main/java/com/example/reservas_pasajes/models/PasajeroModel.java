package com.example.reservas_pasajes.models;

public class PasajeroModel {
    private int idReseva;
    private String tipoDocumento;
    private String numDocumento;
    private String numAsiento;
    private String nombrePasajero;
    private String apellidoPaternoPasajero;
    private String apellidoMaternoPasajero;
    private double precioAsiento;
    private double precioPromocionalAsiento;
    private String generoPasajero;
    public PasajeroModel(){}
    public PasajeroModel(int idReseva, String tipoDocumento, String numDocumento, String numAsiento,
                         String nombrePasajero, String apellidoPaternoPasajero,
                         String apellidoMaternoPasajero, double precioAsiento,
                         double precioPromocionalAsiento, String generoPasajero) {
        this.idReseva = idReseva;
        this.tipoDocumento = tipoDocumento;
        this.numDocumento = numDocumento;
        this.numAsiento = numAsiento;
        this.nombrePasajero = nombrePasajero;
        this.apellidoPaternoPasajero = apellidoPaternoPasajero;
        this.apellidoMaternoPasajero = apellidoMaternoPasajero;
        this.precioAsiento = precioAsiento;
        this.precioPromocionalAsiento = precioPromocionalAsiento;
        this.generoPasajero = generoPasajero;
    }

    public int getIdReseva() {
        return idReseva;
    }

    public void setIdReseva(int idReseva) {
        this.idReseva = idReseva;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public String getNumAsiento() {
        return numAsiento;
    }

    public void setNumAsiento(String numAsiento) {
        this.numAsiento = numAsiento;
    }

    public String getNombrePasajero() {
        return nombrePasajero;
    }

    public void setNombrePasajero(String nombrePasajero) {
        this.nombrePasajero = nombrePasajero;
    }

    public String getApellidoPaternoPasajero() {
        return apellidoPaternoPasajero;
    }

    public void setApellidoPaternoPasajero(String apellidoPaternoPasajero) {
        this.apellidoPaternoPasajero = apellidoPaternoPasajero;
    }

    public String getApellidoMaternoPasajero() {
        return apellidoMaternoPasajero;
    }

    public void setApellidoMaternoPasajero(String apellidoMaternoPasajero) {
        this.apellidoMaternoPasajero = apellidoMaternoPasajero;
    }

    public double getPrecioAsiento() {
        return precioAsiento;
    }

    public void setPrecioAsiento(double precioAsiento) {
        this.precioAsiento = precioAsiento;
    }

    public double getPrecioPromocionalAsiento() {
        return precioPromocionalAsiento;
    }

    public void setPrecioPromocionalAsiento(double precioPromocionalAsiento) {
        this.precioPromocionalAsiento = precioPromocionalAsiento;
    }

    public String getGeneroPasajero() {
        return generoPasajero;
    }

    public void setGeneroPasajero(String generoPasajero) {
        this.generoPasajero = generoPasajero;
    }
}
