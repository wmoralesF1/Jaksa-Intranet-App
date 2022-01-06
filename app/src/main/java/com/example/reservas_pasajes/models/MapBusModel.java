package com.example.reservas_pasajes.models;

public class MapBusModel {
    private String tipo;
    private int indice;
    private String numAsiento;
    private int piso;
    private double precio;
    private int genero;
    private int estado;//0: Ocupado , 1: Libre, 2: Seleccionado


    public MapBusModel(String tipo, int indice,String numAsiento, int piso, double precio, int genero, int estado) {
        this.tipo = tipo;
        this.indice = indice;
        this.numAsiento = numAsiento;
        this.piso = piso;
        this.precio = precio;
        this.genero = genero;
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public String getNumAsiento() {
        return numAsiento;
    }

    public void setNumAsiento(String numAsiento) {
        this.numAsiento = numAsiento;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getGenero() {
        return genero;
    }

    public void setGenero(int genero) {
        this.genero = genero;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

}
