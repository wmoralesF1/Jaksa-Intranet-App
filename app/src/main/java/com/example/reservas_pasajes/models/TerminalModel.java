package com.example.reservas_pasajes.models;

public class TerminalModel {

    private int idTerminal;
    private String nomTerminal;

    public TerminalModel(int idTerminal, String nomTerminal) {
        this.idTerminal = idTerminal;
        this.nomTerminal = nomTerminal;
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

    @Override
    public String toString() {
        return nomTerminal;
    }
}
