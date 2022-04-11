package com.example.reservas_pasajes.models;

public class VentaModel {
    private String nomUsuario;
    private String claveUsuario;
    private int idBoleto;
    private int idViaje;
    private String numBus;
    private String numBoleto;
    private double precio;
    private String numAsiento;
    private String estado;
    private String documentoIdentidad;

    public String getNomUsuario() {
        return nomUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }

    public String getClaveUsuario() {
        return claveUsuario;
    }

    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

    public int getIdBoleto() {
        return idBoleto;
    }

    public void setIdBoleto(int idBoleto) {
        this.idBoleto = idBoleto;
    }

    public int getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(int idViaje) {
        this.idViaje = idViaje;
    }

    public String getNumBus() {
        return numBus;
    }

    public void setNumBus(String numBus) {
        this.numBus = numBus;
    }

    public String getNumBoleto() {
        return numBoleto;
    }

    public void setNumBoleto(String numBoleto) {
        this.numBoleto = numBoleto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getNumAsiento() {
        return numAsiento;
    }

    public void setNumAsiento(String numAsiento) {
        this.numAsiento = numAsiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }
}
