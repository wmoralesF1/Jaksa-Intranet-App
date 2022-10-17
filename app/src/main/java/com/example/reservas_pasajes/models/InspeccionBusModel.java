package com.example.reservas_pasajes.models;

public class InspeccionBusModel {
    private int idViaje;
    private int EmpId;
    private String Zona;
    private int PaxOficina;
    private int PaxPuntosVentas;
    private int PaxRuta;
    private double TotalVentaRuta;
    private String Observacion;

    public int getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(int idViaje) {
        this.idViaje = idViaje;
    }

    public int getEmpId() {
        return EmpId;
    }

    public void setEmpId(int empId) {
        EmpId = empId;
    }

    public String getZona() {
        return Zona;
    }

    public void setZona(String zona) {
        Zona = zona;
    }

    public int getPaxOficina() {
        return PaxOficina;
    }

    public void setPaxOficina(int paxOficina) {
        PaxOficina = paxOficina;
    }

    public int getPaxPuntosVentas() {
        return PaxPuntosVentas;
    }

    public void setPaxPuntosVentas(int paxPuntosVentas) {
        PaxPuntosVentas = paxPuntosVentas;
    }

    public int getPaxRuta() {
        return PaxRuta;
    }

    public void setPaxRuta(int paxRuta) {
        PaxRuta = paxRuta;
    }

    public double getTotalVentaRuta() {
        return TotalVentaRuta;
    }

    public void setTotalVentaRuta(double totalVentaRuta) {
        TotalVentaRuta = totalVentaRuta;
    }

    public String getObservacion() {
        return Observacion;
    }

    public void setObservacion(String observacion) {
        Observacion = observacion;
    }
}
