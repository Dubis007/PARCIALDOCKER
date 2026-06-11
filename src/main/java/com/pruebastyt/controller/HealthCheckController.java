package com.pruebastyt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;

@RestController
@RequestMapping("/api/health")
public class HealthCheckController {

    @Autowired
    private DataSource dataSource;

    @GetMapping
    public ResponseEntity<String> checkHealth() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(1000)) {
                return ResponseEntity.ok("ESTADO OK: ¡El sistema está en línea y conectado a la base de datos 'pruebas_tyt' exitosamente!");
            } else {
                return ResponseEntity.status(500).body("ESTADO ERROR: Fallo en la validación de la conexión a la base de datos.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("ESTADO CRÍTICO: Error de conexión a la base de datos: " + e.getMessage());
        }
    }
}