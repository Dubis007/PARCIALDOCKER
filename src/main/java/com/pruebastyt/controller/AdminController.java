package com.pruebastyt.controller;

import com.pruebastyt.model.Facultad;
import com.pruebastyt.model.Usuario;
import com.pruebastyt.repository.EstudianteRepository;
import com.pruebastyt.repository.FacultadRepository;
import com.pruebastyt.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FacultadRepository facultadRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    // ==========================================
    // DASHBOARD ESTADÍSTICO
    // ==========================================
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalUsuarios", usuarioRepository.count());
        model.addAttribute("totalFacultades", facultadRepository.count());
        model.addAttribute("totalEstudiantes", estudianteRepository.count());
        return "admin/dashboard";
    }

    // ==========================================
    // CRUD FACULTADES
    // ==========================================
    @GetMapping("/facultades")
    public String listarFacultades(Model model) {
        model.addAttribute("facultades", facultadRepository.findAll());
        model.addAttribute("nuevaFacultad", new Facultad());
        return "admin/facultades";
    }

    @PostMapping("/facultades/guardar")
    public String guardarFacultad(@ModelAttribute Facultad facultad) {
        facultadRepository.save(facultad);
        return "redirect:/admin/facultades?exito";
    }

    @PostMapping("/facultades/eliminar/{id}")
    public String eliminarFacultad(@PathVariable Long id) {
        facultadRepository.deleteById(id);
        return "redirect:/admin/facultades?eliminado";
    }

    // ==========================================
    // GESTIÓN DE USUARIOS (NUEVO)
    // ==========================================
    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "admin/usuarios";
    }

    @PostMapping("/usuarios/toggle/{id}")
    public String toggleEstadoUsuario(@PathVariable Long id) {
        Usuario u = usuarioRepository.findById(id).orElseThrow();
        // Si es admin, no permitimos desactivarlo para evitar bloqueos del sistema
        if (!u.getUsername().equals("admin")) {
            u.setActivo(!u.getActivo()); // Invierte el estado (true a false, o false a true)
            usuarioRepository.save(u);
        }
        return "redirect:/admin/usuarios?exito";
    }
}