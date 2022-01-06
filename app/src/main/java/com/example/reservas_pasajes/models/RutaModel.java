package com.example.reservas_pasajes.models;

public class RutaModel {

    private int RutaId;
    private String RutaDescripcion;
    private double RutaPrecio;

    public RutaModel() {
    }

    public RutaModel(int rutaId, String rutaDescripcion, double rutaPrecio) {
        RutaId = rutaId;
        RutaDescripcion = rutaDescripcion;
        RutaPrecio = rutaPrecio;
    }

    public int getRutaId() {
        return RutaId;
    }

    public void setRutaId(int rutaId) {
        RutaId = rutaId;
    }

    public String getRutaDescripcion() {
        return RutaDescripcion;
    }

    public void setRutaDescripcion(String rutaDescripcion) {
        RutaDescripcion = rutaDescripcion;
    }

    public double getRutaPrecio() {
        return RutaPrecio;
    }

    public void setRutaPrecio(double rutaPrecio) {
        RutaPrecio = rutaPrecio;
    }

    @Override
    public String toString() {
        return RutaDescripcion + " :: S/ " + String.valueOf(RutaPrecio);
    }
}
