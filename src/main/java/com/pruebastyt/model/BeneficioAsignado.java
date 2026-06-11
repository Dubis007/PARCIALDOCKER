package com.pruebastyt.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "beneficios_asignados")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BeneficioAsignado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "beneficio_id", nullable = false)
    private Beneficio beneficio;

    @Column(nullable = false)
    private LocalDate fechaAsignacion;

    @Column(nullable = false, length = 20)
    private String estado; // PENDIENTE, APROBADO, RECHAZADO

    // Getters y Setters manuales
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Estudiante getEstudiante() { return estudiante; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }
    public Beneficio getBeneficio() { return beneficio; }
    public void setBeneficio(Beneficio beneficio) { this.beneficio = beneficio; }
    public LocalDate getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(LocalDate fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}