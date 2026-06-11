package com.pruebastyt.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "resultados_pruebas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ResultadoPrueba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", nullable = false, unique = true)
    private Estudiante estudiante;

    @Column(nullable = false, length = 20)
    private String tipoPrueba;

    @Column(nullable = false)
    private Integer puntajeGlobal;

    private Integer puntajeLecturaCritica;
    private Integer puntajeRazonamientoCuantitativo;
    private Integer puntajeCompetenciasCiudadanas;
    private Integer puntajeComunicacionEscrita;
    private Integer puntajeIngles;

    // Getters manuales
    public Long getId() { return id; }
    public Estudiante getEstudiante() { return estudiante; }
    public String getTipoPrueba() { return tipoPrueba; }
    public Integer getPuntajeGlobal() { return puntajeGlobal; }
    public Integer getPuntajeLecturaCritica() { return puntajeLecturaCritica; }
    public Integer getPuntajeRazonamientoCuantitativo() { return puntajeRazonamientoCuantitativo; }
    public Integer getPuntajeCompetenciasCiudadanas() { return puntajeCompetenciasCiudadanas; }
    public Integer getPuntajeComunicacionEscrita() { return puntajeComunicacionEscrita; }
    public Integer getPuntajeIngles() { return puntajeIngles; }

    // Setters manuales
    public void setId(Long id) { this.id = id; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }
    public void setTipoPrueba(String tipoPrueba) { this.tipoPrueba = tipoPrueba; }
    public void setPuntajeGlobal(Integer puntajeGlobal) { this.puntajeGlobal = puntajeGlobal; }
    public void setPuntajeLecturaCritica(Integer puntajeLecturaCritica) { this.puntajeLecturaCritica = puntajeLecturaCritica; }
    public void setPuntajeRazonamientoCuantitativo(Integer puntajeRazonamientoCuantitativo) { this.puntajeRazonamientoCuantitativo = puntajeRazonamientoCuantitativo; }
    public void setPuntajeCompetenciasCiudadanas(Integer puntajeCompetenciasCiudadanas) { this.puntajeCompetenciasCiudadanas = puntajeCompetenciasCiudadanas; }
    public void setPuntajeComunicacionEscrita(Integer puntajeComunicacionEscrita) { this.puntajeComunicacionEscrita = puntajeComunicacionEscrita; }
    public void setPuntajeIngles(Integer puntajeIngles) { this.puntajeIngles = puntajeIngles; }
}