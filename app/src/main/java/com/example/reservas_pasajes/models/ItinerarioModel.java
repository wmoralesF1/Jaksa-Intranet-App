package com.example.reservas_pasajes.models;


import java.util.ArrayList;

public class ItinerarioModel {
    private int idViaje;
    private int idServicio;
    private String nomServicio;
    private String horaReserva;
    private int asientosBus;
    private int asientosBusPrimerPiso;
    private int asientosBusSegundoPiso;
    private int asientosLibres;
    private ArrayList<AsientoModel> listaAsientosOcupados;
    private ArrayList<RutaModel> listaRutas;
    private RutaModel rutaViaje;
    public ItinerarioModel(){}

    public ItinerarioModel(int idViaje, int idServicio, String nomServicio, String horaReserva,
                           int asientosLibres, double precioPrimerNivel, double precioSegundoNivel) {
        this.idViaje = idViaje;
        this.idServicio = idServicio;
        this.nomServicio = nomServicio;
        this.horaReserva = horaReserva;
        this.asientosLibres = asientosLibres;
    }

    public int getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(int idViaje) {
        this.idViaje = idViaje;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public String getNomServicio() {
        return nomServicio;
    }

    public void setNomServicio(String nomServicio) {
        this.nomServicio = nomServicio;
    }

    public String getHoraReserva() {
        return horaReserva;
    }

    public void setHoraReserva(String horaReserva) {
        this.horaReserva = horaReserva;
    }

    public int getAsientosBus() {
        return asientosBus;
    }

    public void setAsientosBus(int asientosBus) {
        this.asientosBus = asientosBus;
    }

    public int getAsientosLibres() {
        return asientosLibres;
    }

    public void setAsientosLibres(int asientosLibres) {
        this.asientosLibres = asientosLibres;
    }

    public int getAsientosBusPrimerPiso() {
        return asientosBusPrimerPiso;
    }

    public void setAsientosBusPrimerPiso(int asientosBusPrimerPiso) {
        this.asientosBusPrimerPiso = asientosBusPrimerPiso;
    }

    public int getAsientosBusSegundoPiso() {
        return asientosBusSegundoPiso;
    }

    public void setAsientosBusSegundoPiso(int asientosBusSegundoPiso) {
        this.asientosBusSegundoPiso = asientosBusSegundoPiso;
    }

    public ArrayList<AsientoModel> getListaAsientosOcupados() {
        return listaAsientosOcupados;
    }

    public void setListaAsientosOcupados(ArrayList<AsientoModel> listaAsientosOcupados) {
        this.listaAsientosOcupados = listaAsientosOcupados;
    }

    public ArrayList<RutaModel> getListaRutas() {
        return listaRutas;
    }

    public void setListaRutas(ArrayList<RutaModel> listaRutas) {
        this.listaRutas = listaRutas;
    }

    public RutaModel getRutaViaje() {
        return rutaViaje;
    }

    public void setRutaViaje(RutaModel rutaViaje) {
        this.rutaViaje = rutaViaje;
    }
}
