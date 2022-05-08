package com.example.reservas_pasajes.models;

import java.util.ArrayList;

public class UsuarioModel {
    private int idUsuario;
    private String nomUsuario;
    private String passUsuario;
    private String pass2Usuario;
    private String numeroDocumentoUsuario;
    private int idTerminal;
    private String nomTerminal;
    private String centroOperaciones;
    private String estado;
    private String nivel;
    private ArrayList<MenuModel> listaMenu;
    public void UsuarioModel(){}

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomUsuario() {
        return nomUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }

    public String getPassUsuario() {
        return passUsuario;
    }

    public void setPassUsuario(String passUsuario) {
        this.passUsuario = passUsuario;
    }

    public String getPass2Usuario() {
        return pass2Usuario;
    }

    public void setPass2Usuario(String pass2Usuario) {
        this.pass2Usuario = pass2Usuario;
    }

    public String getNumeroDocumentoUsuario() {
        return numeroDocumentoUsuario;
    }

    public void setNumeroDocumentoUsuario(String numeroDocumentoUsuario) {
        this.numeroDocumentoUsuario = numeroDocumentoUsuario;
    }

    public int getIdTerminal() {
        return idTerminal;
    }

    public void setIdTerminal(int idTerminal) {
        this.idTerminal = idTerminal;
    }

    public String getNomTerminal() {
        return nomTerminal;
    }

    public void setNomTerminal(String nomTerminal) {
        this.nomTerminal = nomTerminal;
    }

    public String getCentroOperaciones() {
        return centroOperaciones;
    }

    public void setCentroOperaciones(String centroOperaciones) {
        this.centroOperaciones = centroOperaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public ArrayList<MenuModel> getListaMenu() {
        return listaMenu;
    }

    public void setListaMenu(ArrayList<MenuModel> listaMenu) {
        this.listaMenu = listaMenu;
    }

    @Override
    public String toString() {
        return this.nomUsuario;
    }
}
