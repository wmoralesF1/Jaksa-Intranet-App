package com.example.reservas_pasajes.models;

import java.util.ArrayList;

public class ItinerarioModel {
    private int idViaje;
    private int idOrigen;
    private String nomOrigen;
    private String nomEmbarque;
    private int idDestino;
    private String nomDestino;
    private int idServicio;
    private String nomServicio;
    private String fechaReserva;
    private String fechaReservaFormat;
    private String horaReserva;
    private int asientosBus;
    private int asientosBusPrimerPiso;
    private int asientosBusSegundoPiso;
    private int asientosLibres;
    private ArrayList<AsientoModel> listaAsientosOcupados;
    private ArrayList<RutaModel> listaRutas;
    private RutaModel rutaViaje;
    private ArrayList<PasajeroModel> listaPasajeros;

    public ItinerarioModel(){}

    public int getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(int idViaje) {
        this.idViaje = idViaje;
    }

    public int getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(int idOrigen) {
        this.idOrigen = idOrigen;
    }

    public String getNomOrigen() {
        return nomOrigen;
    }

    public void setNomOrigen(String nomOrigen) {
        this.nomOrigen = nomOrigen;
    }

    public String getNomEmbarque() {
        return nomEmbarque;
    }

    public void setNomEmbarque(String nomEmbarque) {
        this.nomEmbarque = nomEmbarque;
    }

    public int getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(int idDestino) {
        this.idDestino = idDestino;
    }

    public String getNomDestino() {
        return nomDestino;
    }

    public void setNomDestino(String nomDestino) {
        this.nomDestino = nomDestino;
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

    public String getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(String fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public String getFechaReservaFormat() {
        return fechaReservaFormat;
    }

    public void setFechaReservaFormat(String fechaReservaFormat) {
        this.fechaReservaFormat = fechaReservaFormat;
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

    public ArrayList<PasajeroModel> getListaPasajeros() {
        return listaPasajeros;
    }

    public void setListaPasajeros(ArrayList<PasajeroModel> listaPasajeros) {
        this.listaPasajeros = listaPasajeros;
    }
}
