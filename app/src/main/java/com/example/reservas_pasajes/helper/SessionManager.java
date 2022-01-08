package com.example.reservas_pasajes.helper;

import android.app.Application;

import com.example.reservas_pasajes.models.ItinerarioModel;
import com.example.reservas_pasajes.models.PasajeroModel;
import com.example.reservas_pasajes.models.TerminalModel;
import com.example.reservas_pasajes.models.UsuarioModel;
import com.example.reservas_pasajes.models.ViajeModel;

import java.util.ArrayList;

public class SessionManager extends Application {

    private static ArrayList<TerminalModel> listaTerminales;
    private static UsuarioModel usuario;
    private static ArrayList<ItinerarioModel> salidaTurnos;
    private static ViajeModel viaje;
    private static ItinerarioModel turnoViaje;


    public static ArrayList<TerminalModel> getListaTerminales() {
        return listaTerminales;
    }

    public static void setListaTerminales(ArrayList<TerminalModel> listaTerminales) {
        SessionManager.listaTerminales = listaTerminales;
    }

    public static UsuarioModel getUsuario() {
        return usuario;
    }

    public static void setUsuario(UsuarioModel usuario) {
        SessionManager.usuario = usuario;
    }

    public static ArrayList<ItinerarioModel> getSalidaTurnos() {
        return salidaTurnos;
    }

    public static void setSalidaTurnos(ArrayList<ItinerarioModel> salidaTurnos) {
        SessionManager.salidaTurnos = salidaTurnos;
    }

    public static ViajeModel getViaje() {
        return viaje;
    }

    public static void setViaje(ViajeModel viaje) {
        SessionManager.viaje = viaje;
    }

    public static ItinerarioModel getTurnoViaje() {
        return turnoViaje;
    }

    public static void setTurnoViaje(ItinerarioModel turnoViaje) {
        SessionManager.turnoViaje = turnoViaje;
    }
}
