package com.pruebastyt.controller;

import com.pruebastyt.model.Estudiante;
import com.pruebastyt.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/docente")
public class DocenteController {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "docente/dashboard";
    }

    @GetMapping("/consultas")
    public String consultas(@RequestParam(required = false) String documento, Model model) {
        List<Estudiante> estudiantes = new ArrayList<>();
        
        // Si el docente buscó un documento específico, filtramos
        if (documento != null && !documento.trim().isEmpty()) {
            Optional<Estudiante> est = estudianteRepository.findByDocumento(documento);
            est.ifPresent(estudiantes::add);
            model.addAttribute("busquedaActiva", true);
        } else {
            // Si no hay búsqueda, mostramos todos (o podríamos limitar a los de su facultad)
            estudiantes = estudianteRepository.findAll();
            model.addAttribute("busquedaActiva", false);
        }
        
        model.addAttribute("estudiantes", estudiantes);
        model.addAttribute("documentoBuscado", documento);
        return "docente/consultas";
    }
}