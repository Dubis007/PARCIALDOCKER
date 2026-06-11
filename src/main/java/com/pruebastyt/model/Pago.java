package com.pruebastyt.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "pagos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;

    @Column(nullable = false)
    private Double montoOriginal;

    @Column(nullable = false)
    private Double montoFinal; // Monto después de aplicar beneficio

    @Column(nullable = false, length = 20)
    private String estado; // PENDIENTE, PAGADO

    private LocalDate fechaPago;

    @Column(length = 255)
    private String rutaComprobantePdf; // Ruta donde se guardará el PDF subido
}