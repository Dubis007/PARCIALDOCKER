package com.pruebastyt.config;

import com.pruebastyt.model.*;
import com.pruebastyt.repository.*;
import com.pruebastyt.seed.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SeedService seedService;

    @Bean
    public CommandLineRunner initData(UsuarioRepository usuarioRepository, RolRepository rolRepository, 
                                      BeneficioRepository beneficioRepository) {
        return args -> {
            System.out.println("=======================================================");
            System.out.println("⚙️ INICIANDO CONFIGURACIÓN DEL SISTEMA...");
            
            // 1. Crear Roles
            Rol rolAdmin = rolRepository.findByNombre("ROLE_ADMIN").orElseGet(() -> { Rol r = new Rol(); r.setNombre("ROLE_ADMIN"); return rolRepository.save(r); });
            rolRepository.findByNombre("ROLE_COORDINADOR").orElseGet(() -> { Rol r = new Rol(); r.setNombre("ROLE_COORDINADOR"); return rolRepository.save(r); });
            rolRepository.findByNombre("ROLE_DOCENTE").orElseGet(() -> { Rol r = new Rol(); r.setNombre("ROLE_DOCENTE"); return rolRepository.save(r); });
            rolRepository.findByNombre("ROLE_ESTUDIANTE").orElseGet(() -> { Rol r = new Rol(); r.setNombre("ROLE_ESTUDIANTE"); return rolRepository.save(r); });

            // 2. Crear Beneficios Base
            if (beneficioRepository.count() == 0) {
                Beneficio b1 = new Beneficio(); b1.setNombre("Beca Excelencia"); b1.setDescripcion("Descuento total por obtener un puntaje global superior a 200 puntos."); b1.setPorcentajeDescuento(100.0); beneficioRepository.save(b1);
                Beneficio b2 = new Beneficio(); b2.setNombre("Mención Honorífica"); b2.setDescripcion("Descuento parcial por obtener un puntaje global superior a 180 puntos."); b2.setPorcentajeDescuento(50.0); beneficioRepository.save(b2);
                Beneficio b3 = new Beneficio(); b3.setNombre("Reconocimiento Académico"); b3.setDescripcion("Descuento básico por obtener un puntaje global superior a 160 puntos."); b3.setPorcentajeDescuento(25.0); beneficioRepository.save(b3);
            }

            // 3. Crear Admin Principal
            if (!usuarioRepository.existsByUsername("admin")) {
                Usuario admin = new Usuario(); admin.setUsername("admin"); admin.setPassword(passwordEncoder.encode("12345")); admin.setActivo(true); admin.setRol(rolAdmin); 
                usuarioRepository.save(admin);
                System.out.println("✅ Usuario ADMIN creado (admin / 12345)");
            }

            // 4. Delegar la creación de Facultades, Coordinadores, Docentes y Estudiantes al SeedService
            seedService.generarDatosSemilla();
            
            System.out.println("⚙️ SISTEMA LISTO Y CONFIGURADO.");
            System.out.println("=======================================================");
        };
    }
}