package com.pruebastyt.controller;

import com.pruebastyt.model.Estudiante;
import com.pruebastyt.model.Usuario;
import com.pruebastyt.repository.EstudianteRepository;
import com.pruebastyt.repository.UsuarioRepository;
import com.pruebastyt.service.ImportadorService;
import com.pruebastyt.util.ExcelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/coordinador")
public class CoordinadorController {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private ImportadorService importadorService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalEstudiantes", estudianteRepository.count());
        return "coordinador/dashboard";
    }

    @GetMapping("/estudiantes")
    public String listarEstudiantes(Model model) {
        model.addAttribute("estudiantes", estudianteRepository.findAll());
        return "coordinador/estudiantes";
    }

    @GetMapping("/importar")
    public String vistaImportar() {
        return "coordinador/importar";
    }

    @PostMapping("/importar")
    public String procesarImportacion(@RequestParam("archivoExcel") MultipartFile archivoExcel) {
        if (archivoExcel.isEmpty() || !ExcelHelper.hasExcelFormat(archivoExcel)) {
            return "redirect:/coordinador/importar?error";
        }

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario coordinador = usuarioRepository.findByUsername(auth.getName()).orElseThrow();

            importadorService.procesarExcel(archivoExcel, coordinador);
            
            return "redirect:/coordinador/importar?exito";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/coordinador/importar?error";
        }
    }

    @PostMapping("/estudiantes/aprobar/{id}")
    public String aprobarGradoEstudiante(@PathVariable Long id) {
        Estudiante estudiante = estudianteRepository.findById(id).orElseThrow();
        estudiante.setEstadoGrado("APROBADO");
        estudianteRepository.save(estudiante);
        return "redirect:/coordinador/estudiantes?exito";
    }
}