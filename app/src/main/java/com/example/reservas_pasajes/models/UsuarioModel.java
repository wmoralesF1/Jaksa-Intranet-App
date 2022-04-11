package com.example.reservas_pasajes.models;

import java.util.ArrayList;

public class UsuarioModel {
    private int idUsuario;
    private String nomUsuario;
    private String passUsuario;

    private int idTerminal;
    private String nomTerminal;
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
