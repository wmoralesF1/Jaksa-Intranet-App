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
    private String tipoVenta;
    private String nomRutaViaje;
    private int numPax;
    private double montoPax;
    private String fechaViaje;
    private String fechaViajeFormat;
    private String horaViaje;
    private String fechaReserva;
    private String fechaReservaFormat;
    private String horaReserva;
    private double precioAsiento;
    private int idBus;
    private String numBus;
    private int numAsientos;
    private String paxTerminalLima;
    private String paxTerminalChincha;
    private String paxTerminalPisco;
    private String paxTerminalIca;
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

    public void setTramoViaje(String tramoViaje) {
        this.tramoViaje = tramoViaje;
    }

    public String getIdTramoViaje() {
        return idTramoViaje;
    }

    public void setIdTramoViaje(String idTramoViaje) {
        this.idTramoViaje = idTramoViaje;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public String getNomRutaViaje() {
        return nomRutaViaje;
    }

    public void setNomRutaViaje(String nomRutaViaje) {
        this.nomRutaViaje = nomRutaViaje;
    }

    public int getNumPax() {
        return numPax;
    }

    public void setNumPax(int numPax) {
        this.numPax = numPax;
    }

    public double getMontoPax() {
        return montoPax;
    }

    public void setMontoPax(double montoPax) {
        this.montoPax = montoPax;
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

    public int getIdBus() {
        return idBus;
    }

    public void setIdBus(int idBus) {
        this.idBus = idBus;
    }

    public String getNumBus() {
        return numBus;
    }

    public void setNumBus(String numBus) {
        this.numBus = numBus;
    }

    public int getNumAsientos() {
        return numAsientos;
    }

    public void setNumAsientos(int numAsientos) {
        this.numAsientos = numAsientos;
    }

    public String getPaxTerminalLima() {
        return paxTerminalLima;
    }

    public void setPaxTerminalLima(String paxTerminalLima) {
        this.paxTerminalLima = paxTerminalLima;
    }

    public String getPaxTerminalChincha() {
        return paxTerminalChincha;
    }

    public void setPaxTerminalChincha(String paxTerminalChincha) {
        this.paxTerminalChincha = paxTerminalChincha;
    }

    public String getPaxTerminalPisco() {
        return paxTerminalPisco;
    }

    public void setPaxTerminalPisco(String paxTerminalPisco) {
        this.paxTerminalPisco = paxTerminalPisco;
    }

    public String getPaxTerminalIca() {
        return paxTerminalIca;
    }

    public void setPaxTerminalIca(String paxTerminalIca) {
        this.paxTerminalIca = paxTerminalIca;
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
