package com.pruebastyt.seed;

import com.pruebastyt.model.*;
import com.pruebastyt.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeedService {

    @Autowired private RolRepository rolRepository;
    @Autowired private FacultadRepository facultadRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private CoordinadorRepository coordinadorRepository;
    @Autowired private DocenteRepository docenteRepository;
    @Autowired private EstudianteRepository estudianteRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Transactional
    public void generarDatosSemilla() {
        System.out.println("🌱 VERIFICANDO Y GENERANDO DATOS SEMILLA...");

        Rol rolCoord = rolRepository.findByNombre("ROLE_COORDINADOR").orElseThrow();
        Rol rolDocente = rolRepository.findByNombre("ROLE_DOCENTE").orElseThrow();
        Rol rolEstudiante = rolRepository.findByNombre("ROLE_ESTUDIANTE").orElseThrow();

        // 1. Crear 4 Facultades
        String[] nombresFacultades = {"Ingeniería", "Ciencias de la Salud", "Ciencias Empresariales", "Artes y Humanidades"};
        List<Facultad> facultades = new ArrayList<>();
        
        for (String nombre : nombresFacultades) {
            Facultad f = facultadRepository.findByNombre(nombre).orElseGet(() -> {
                Facultad nueva = new Facultad();
                nueva.setNombre(nombre);
                return facultadRepository.save(nueva);
            });
            facultades.add(f);
        }

        // 2. Crear Coordinadores y Docentes (coord1, coord2... docente1, docente2...)
        for (int i = 0; i < facultades.size(); i++) {
            Facultad fac = facultades.get(i);
            int numero = i + 1;

            // Coordinador
            String userCoord = "coord" + numero;
            if (!usuarioRepository.existsByUsername(userCoord)) {
                Usuario u = new Usuario(); u.setUsername(userCoord); u.setPassword(passwordEncoder.encode("12345")); u.setActivo(true); u.setRol(rolCoord);
                Coordinador c = new Coordinador(); c.setDocumento("200" + numero); c.setNombres("Coordinador " + numero); c.setApellidos(fac.getNombre()); c.setFacultad(fac); c.setUsuario(u);
                coordinadorRepository.save(c);
                System.out.println("✅ Creado Coordinador: " + userCoord);
            }

            // Docente
            String userDoc = "docente" + numero;
            if (!usuarioRepository.existsByUsername(userDoc)) {
                Usuario u = new Usuario(); u.setUsername(userDoc); u.setPassword(passwordEncoder.encode("12345")); u.setActivo(true); u.setRol(rolDocente);
                Docente d = new Docente(); d.setDocumento("300" + numero); d.setNombres("Docente " + numero); d.setApellidos(fac.getNombre()); d.setFacultad(fac); d.setUsuario(u);
                docenteRepository.save(d);
            }
        }

        // 3. Crear 35 Estudiantes Secuenciales (Para cruzar con el Excel)
        String[] nombres = {"Carlos", "Ana", "Luis", "María", "Jorge", "Laura", "Pedro", "Sofía", "Diego", "Elena", "Miguel", "Lucía", "David", "Carmen", "Raúl", "Paula", "Andrés", "Daniela", "Fernando", "Valeria"};
        String[] apellidos = {"Gómez", "Rodríguez", "Fernández", "López", "Martínez", "Pérez", "García", "Sánchez", "Romero", "Torres", "Ruiz", "Díaz", "Álvarez", "Moreno", "Muñoz", "Rojas", "Vargas", "Castro", "Ortiz", "Silva"};

        int creados = 0;
        for (int i = 1; i <= 35; i++) {
            String documento = String.valueOf(100200300 + i); // 100200301, 100200302...
            String username = "est" + i;
            Facultad fac = facultades.get(i % 4);

            if (!usuarioRepository.existsByUsername(username)) {
                Usuario u = new Usuario();
                u.setUsername(username);
                u.setPassword(passwordEncoder.encode("12345"));
                u.setActivo(true);
                u.setRol(rolEstudiante);

                Estudiante e = new Estudiante();
                e.setDocumento(documento);
                e.setCodigo("2024" + String.format("%03d", i));
                e.setNombres(nombres[i % nombres.length]);
                e.setApellidos(apellidos[i % apellidos.length]);
                e.setSemestre(10);
                e.setFacultad(fac);
                e.setUsuario(u);

                estudianteRepository.save(e);
                creados++;
            }
        }
        
        if (creados > 0) {
            System.out.println("✅ Se crearon " + creados + " estudiantes nuevos para el Excel.");
        }
    }
}