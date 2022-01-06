package com.example.reservas_pasajes.models;

import java.util.ArrayList;

public class ViajeModel {
    private int idViaje;
    private int idOrigen;
    private String nomOrigen;
    private int idDestino;
    private String nomDestino;
    private int idServicio;
    private String nomServicio;
    private String fechaReserva;
    private String fechaReservaFormat;
    private String horaReserva;
    private double precioAsiento;
    private ArrayList<PasajeroModel> listaPasajero;
    private RutaModel rutaViaje;
    private ItinerarioModel itinerarioViaje;

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

    public double getPrecioAsiento() {
        return precioAsiento;
    }

    public void setPrecioAsiento(double precioAsiento) {
        this.precioAsiento = precioAsiento;
    }

    public ArrayList<PasajeroModel> getListaPasajero() {
        return listaPasajero;
    }

    public void setListaPasajero(ArrayList<PasajeroModel> listaPasajero) {
        this.listaPasajero = listaPasajero;
    }

    public RutaModel getRutaViaje() {
        return rutaViaje;
    }

    public void setRutaViaje(RutaModel rutaViaje) {
        this.rutaViaje = rutaViaje;
    }

    public ItinerarioModel getItinerarioViaje() {
        return itinerarioViaje;
    }

    public void setItinerarioViaje(ItinerarioModel itinerarioViaje) {
        this.itinerarioViaje = itinerarioViaje;
    }
}
