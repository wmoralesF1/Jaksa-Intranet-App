package com.example.reservas_pasajes.helper;

public class Enumeraciones {

    public enum EstadoCivil
    {
        SOLTERO("S","SOLTERO"), CASADO("C","CASADO"),
        DIVORCIADO("D","DIVORCIADO");
        private String idEstadoCivil;
        private String nombreEstadoCivil;
        private EstadoCivil (String idEstadoCivil, String nombreEstadoCivil){
            this.idEstadoCivil = idEstadoCivil;
            this.nombreEstadoCivil = nombreEstadoCivil;
        }

        public String getIdEstadoCivil() {
            return idEstadoCivil;
        }

        public void setIdEstadoCivil(String idEstadoCivil) {
            this.idEstadoCivil = idEstadoCivil;
        }

        public String getNombreEstadoCivil() {
            return nombreEstadoCivil;
        }

        public void setNombreEstadoCivil(String nombreEstadoCivil) {
            this.nombreEstadoCivil = nombreEstadoCivil;
        }
    }

    public enum CategoriaLicencia
    {
        A2B("A2B","A2B"), A3B("A3B","A3B");
        private String idCategoriaLicencia;
        private String nombreCategoriaLicencia;
        private CategoriaLicencia (String idCategoriaLicencia, String nombreCategoriaLicencia){
            this.idCategoriaLicencia = idCategoriaLicencia;
            this.nombreCategoriaLicencia = nombreCategoriaLicencia;
        }

        public String getIdCategoriaLicencia() {
            return idCategoriaLicencia;
        }

        public void setIdCategoriaLicencia(String idCategoriaLicencia) {
            this.idCategoriaLicencia = idCategoriaLicencia;
        }

        public String getNombreCategoriaLicencia() {
            return nombreCategoriaLicencia;
        }

        public void setNombreCategoriaLicencia(String nombreCategoriaLicencia) {
            this.nombreCategoriaLicencia = nombreCategoriaLicencia;
        }
    }

    public enum Estado
    {
        ACTIVO("ACTIVO","ACTIVO"), INACTIVO("INACTIVO","INACTIVO");
        private String idEstado;
        private String nombreEstado;
        private Estado (String idEstado, String nombreEstado){
            this.idEstado = idEstado;
            this.nombreEstado = nombreEstado;
        }

        public String getIdEstado() {
            return idEstado;
        }

        public void setIdEstado(String idEstado) {
            this.idEstado = idEstado;
        }

        public String getNombreEstado() {
            return nombreEstado;
        }

        public void setNombreEstado(String nombreEstado) {
            this.nombreEstado = nombreEstado;
        }
    }

}
