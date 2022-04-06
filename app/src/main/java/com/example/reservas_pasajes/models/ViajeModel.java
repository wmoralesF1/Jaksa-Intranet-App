package com.example.reservas_pasajes.models;

import java.util.ArrayList;

public class ViajeModel {
    private String nomUsuario;
    private String claveUsuario;
    private int idViaje;
    private String nomTerminal;
    private int idOrigen;
    private String nomOrigen;
    private int idDestino;
    private String nomDestino;
    private String idServicio;
    private String nomServicio;
    private String tramoViaje;
    private String idTramoViaje;
    private String fechaViaje;
    private String fechaViajeFormat;
    private String horaViaje;
    private String fechaReserva;
    private String fechaReservaFormat;
    private String horaReserva;
    private double precioAsiento;
    private String numBus;
    private ArrayList<PasajeroModel> listaPasajero;
    private RutaModel rutaViaje;
    private ItinerarioModel itinerarioViaje;

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

    public int getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(int idViaje) {
        this.idViaje = idViaje;
    }

    public String getNomTerminal() {
        return nomTerminal;
    }

    public void setNomTerminal(String nomTerminal) {
        this.nomTerminal = nomTerminal;
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

    public String getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    public String getNomServicio() {
        return nomServicio;
    }

    public void setNomServicio(String nomServicio) {
        this.nomServicio = nomServicio;
    }

    public String getTramoViaje() {
        return tramoViaje;
    }

    public void setTramoViaje(String tramaViaje) {
        this.tramoViaje = tramaViaje;
    }
    public String getIdTramoViaje() {
        return idTramoViaje;
    }

    public void setIdTramoViaje(String idTramaViaje) {
        this.idTramoViaje = idTramaViaje;
    }

    public String getNumBus() {
        return numBus;
    }

    public void setNumBus(String numBus) {
        this.numBus = numBus;
    }

    public String getFechaViaje() {
        return fechaViaje;
    }

    public void setFechaViaje(String fechaViaje) {
        this.fechaViaje = fechaViaje;
    }

    public String getFechaViajeFormat() {
        return fechaViajeFormat;
    }

    public void setFechaViajeFormat(String fechaViajeFormat) {
        this.fechaViajeFormat = fechaViajeFormat;
    }

    public String getHoraViaje() {
        return horaViaje;
    }

    public void setHoraViaje(String horaViaje) {
        this.horaViaje = horaViaje;
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
