package com.pruebastyt.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "importaciones_excel")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ImportacionExcel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nombreArchivo;

    @Column(nullable = false)
    private LocalDateTime fechaImportacion;

    @Column(nullable = false)
    private Integer totalRegistros;

    @Column(nullable = false)
    private Integer registrosProcesados;

    @Column(nullable = false)
    private Integer registrosConError;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Getters manuales
    public Long getId() { return id; }
    public String getNombreArchivo() { return nombreArchivo; }
    public LocalDateTime getFechaImportacion() { return fechaImportacion; }
    public Integer getTotalRegistros() { return totalRegistros; }
    public Integer getRegistrosProcesados() { return registrosProcesados; }
    public Integer getRegistrosConError() { return registrosConError; }
    public Usuario getUsuario() { return usuario; }

    // Setters manuales
    public void setId(Long id) { this.id = id; }
    public void setNombreArchivo(String nombreArchivo) { this.nombreArchivo = nombreArchivo; }
    public void setFechaImportacion(LocalDateTime fechaImportacion) { this.fechaImportacion = fechaImportacion; }
    public void setTotalRegistros(Integer totalRegistros) { this.totalRegistros = totalRegistros; }
    public void setRegistrosProcesados(Integer registrosProcesados) { this.registrosProcesados = registrosProcesados; }
    public void setRegistrosConError(Integer registrosConError) { this.registrosConError = registrosConError; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}