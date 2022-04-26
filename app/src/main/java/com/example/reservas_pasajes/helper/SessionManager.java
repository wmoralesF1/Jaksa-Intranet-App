package com.example.reservas_pasajes.helper;

import android.app.Application;

import com.example.reservas_pasajes.models.CargoModel;
import com.example.reservas_pasajes.models.GrupoEmpresarialModel;
import com.example.reservas_pasajes.models.ItinerarioModel;
import com.example.reservas_pasajes.models.LocalModel;
import com.example.reservas_pasajes.models.PaisModel;
import com.example.reservas_pasajes.models.PasajeroModel;
import com.example.reservas_pasajes.models.TerminalModel;
import com.example.reservas_pasajes.models.TipoDocumentoModel;
import com.example.reservas_pasajes.models.TipoServicioModel;
import com.example.reservas_pasajes.models.UsuarioModel;
import com.example.reservas_pasajes.models.ViajeModel;

import java.util.ArrayList;
import java.util.List;

public class SessionManager extends Application {

    private static ArrayList<TerminalModel> listaTerminales;
    private static UsuarioModel usuario;
    private static ArrayList<ItinerarioModel> salidaTurnos;
    private static ViajeModel viaje;
    private static ItinerarioModel turnoViaje;
    private static ArrayList<TipoDocumentoModel> listaTiposDocumentos;
    private static List<TipoDocumentoModel> listadoTiposDocumentos;
    private static List<CargoModel> listadoCargos;
    private static List<GrupoEmpresarialModel> listadoGruposEmpresariales;
    private static List<LocalModel> listadoLocales;
    private static List<PaisModel> listadoPaises;
    private static List<TipoServicioModel> listadoTipoServicios;

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

    public static ArrayList<TipoDocumentoModel> getListaTiposDocumentos() {
        return listaTiposDocumentos;
    }

    public static void setListaTiposDocumentos(ArrayList<TipoDocumentoModel> listaTiposDocumentos) {
        SessionManager.listaTiposDocumentos = listaTiposDocumentos;
    }

    public static List<TipoDocumentoModel> getListadoTiposDocumentos() {
        return listadoTiposDocumentos;
    }

    public static void setListadoTiposDocumentos(List<TipoDocumentoModel> listadoTiposDocumentos) {
        SessionManager.listadoTiposDocumentos = listadoTiposDocumentos;
    }

    public static List<CargoModel> getListadoCargos() {
        return listadoCargos;
    }

    public static void setListadoCargos(List<CargoModel> listadoCargos) {
        SessionManager.listadoCargos = listadoCargos;
    }

    public static List<GrupoEmpresarialModel> getListadoGruposEmpresariales() {
        return listadoGruposEmpresariales;
    }

    public static void setListadoGruposEmpresariales(List<GrupoEmpresarialModel> listadoGruposEmpresariales) {
        SessionManager.listadoGruposEmpresariales = listadoGruposEmpresariales;
    }

    public static List<LocalModel> getListadoLocales() {
        return listadoLocales;
    }

    public static void setListadoLocales(List<LocalModel> listadoLocales) {
        SessionManager.listadoLocales = listadoLocales;
    }

    public static List<PaisModel> getListadoPaises() {
        return listadoPaises;
    }

    public static void setListadoPaises(List<PaisModel> listadoPaises) {
        SessionManager.listadoPaises = listadoPaises;
    }

    public static List<TipoServicioModel> getListadoTipoServicios() {
        return listadoTipoServicios;
    }

    public static void setListadoTipoServicios(List<TipoServicioModel> listadoTipoServicios) {
        SessionManager.listadoTipoServicios = listadoTipoServicios;
    }
}
