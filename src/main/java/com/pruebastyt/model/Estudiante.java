package com.pruebastyt.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estudiantes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String documento;

    @Column(unique = true, nullable = false, length = 50)
    private String codigo;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(nullable = false)
    private Integer semestre;

    @Column(length = 20)
    private String estadoGrado = "PENDIENTE";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "facultad_id", nullable = false)
    private Facultad facultad;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    // ==========================================
    // Getters manuales
    // ==========================================
    public Long getId() { return id; }
    public String getDocumento() { return documento; }
    public String getCodigo() { return codigo; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public Integer getSemestre() { return semestre; }
    public String getEstadoGrado() { return estadoGrado; }
    public Facultad getFacultad() { return facultad; }
    public Usuario getUsuario() { return usuario; }

    // ==========================================
    // Setters manuales
    // ==========================================
    public void setId(Long id) { this.id = id; }
    public void setDocumento(String documento) { this.documento = documento; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public void setSemestre(Integer semestre) { this.semestre = semestre; }
    public void setEstadoGrado(String estadoGrado) { this.estadoGrado = estadoGrado; }
    public void setFacultad(Facultad facultad) { this.facultad = facultad; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}