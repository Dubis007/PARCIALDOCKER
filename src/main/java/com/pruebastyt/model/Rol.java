package com.pruebastyt.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String nombre;

    // Getters manuales
    public String getNombre() {
        return nombre;
    }

    // Setters manuales
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}