package com.example.reservas_pasajes.models;

import android.graphics.drawable.Drawable;

public class MenuModel {
    protected Drawable Imagen;
    protected String TextoPrimario;
    protected String TextoSecundario;
    protected String Clave;
    protected int id;
    protected int idMenuPadre;
    public MenuModel(){};
    public MenuModel(Drawable imagen, String textoPrimario, String textoSecundario, String clave, int id, int idMenuPadre) {
        Imagen = imagen;
        TextoPrimario = textoPrimario;
        TextoSecundario = textoSecundario;
        Clave = clave;
        this.id = id;
        this.idMenuPadre = idMenuPadre;
    }

    public Drawable getImagen() {
        return Imagen;
    }

    public void setImagen(Drawable imagen) {
        Imagen = imagen;
    }

    public String getTextoPrimario() {
        return TextoPrimario;
    }

    public void setTextoPrimario(String textoPrimario) {
        TextoPrimario = textoPrimario;
    }

    public String getTextoSecundario() {
        return TextoSecundario;
    }

    public void setTextoSecundario(String textoSecundario) {
        TextoSecundario = textoSecundario;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMenuPadre() {
        return idMenuPadre;
    }

    public void setIdMenuPadre(int idMenuPadre) {
        this.idMenuPadre = idMenuPadre;
    }
}
