package com.example.reservas_pasajes.models;

public class TipoDocumentoModel {

    private int idTipoDocumento;
    private String nomTipoDocumento;

    TipoDocumentoModel(){}

    public TipoDocumentoModel(int idTipoDocumento, String nomTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
        this.nomTipoDocumento = nomTipoDocumento;
    }

    public int getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(int idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getNomTipoDocumento() {
        return nomTipoDocumento;
    }

    public void setNomTipoDocumento(String nomTipoDocumento) {
        this.nomTipoDocumento = nomTipoDocumento;
    }

    @Override
    public String toString() {
        return nomTipoDocumento;
    }

}
