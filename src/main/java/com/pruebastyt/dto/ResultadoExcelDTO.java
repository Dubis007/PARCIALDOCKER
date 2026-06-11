package com.pruebastyt.dto;

public class ResultadoExcelDTO {
    private String documentoEstudiante;
    private String tipoPrueba;
    private Integer puntajeGlobal;
    private Integer puntajeLecturaCritica;
    private Integer puntajeRazonamientoCuantitativo;
    private Integer puntajeCompetenciasCiudadanas;
    private Integer puntajeComunicacionEscrita;
    private Integer puntajeIngles;

    // Getters
    public String getDocumentoEstudiante() { return documentoEstudiante; }
    public String getTipoPrueba() { return tipoPrueba; }
    public Integer getPuntajeGlobal() { return puntajeGlobal; }
    public Integer getPuntajeLecturaCritica() { return puntajeLecturaCritica; }
    public Integer getPuntajeRazonamientoCuantitativo() { return puntajeRazonamientoCuantitativo; }
    public Integer getPuntajeCompetenciasCiudadanas() { return puntajeCompetenciasCiudadanas; }
    public Integer getPuntajeComunicacionEscrita() { return puntajeComunicacionEscrita; }
    public Integer getPuntajeIngles() { return puntajeIngles; }

    // Setters
    public void setDocumentoEstudiante(String documentoEstudiante) { this.documentoEstudiante = documentoEstudiante; }
    public void setTipoPrueba(String tipoPrueba) { this.tipoPrueba = tipoPrueba; }
    public void setPuntajeGlobal(Integer puntajeGlobal) { this.puntajeGlobal = puntajeGlobal; }
    public void setPuntajeLecturaCritica(Integer puntajeLecturaCritica) { this.puntajeLecturaCritica = puntajeLecturaCritica; }
    public void setPuntajeRazonamientoCuantitativo(Integer puntajeRazonamientoCuantitativo) { this.puntajeRazonamientoCuantitativo = puntajeRazonamientoCuantitativo; }
    public void setPuntajeCompetenciasCiudadanas(Integer puntajeCompetenciasCiudadanas) { this.puntajeCompetenciasCiudadanas = puntajeCompetenciasCiudadanas; }
    public void setPuntajeComunicacionEscrita(Integer puntajeComunicacionEscrita) { this.puntajeComunicacionEscrita = puntajeComunicacionEscrita; }
    public void setPuntajeIngles(Integer puntajeIngles) { this.puntajeIngles = puntajeIngles; }
}