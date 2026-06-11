package com.pruebastyt.controller;

import com.pruebastyt.model.BeneficioAsignado;
import com.pruebastyt.model.Estudiante;
import com.pruebastyt.model.ResultadoPrueba;
import com.pruebastyt.model.Usuario;
import com.pruebastyt.repository.BeneficioAsignadoRepository;
import com.pruebastyt.repository.EstudianteRepository;
import com.pruebastyt.repository.ResultadoPruebaRepository;
import com.pruebastyt.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/estudiante")
public class EstudianteController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private ResultadoPruebaRepository resultadoPruebaRepository;

    @Autowired
    private BeneficioAsignadoRepository beneficioAsignadoRepository;

    // Método auxiliar para obtener el estudiante logueado
    private Estudiante getEstudianteLogueado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow();
        return estudianteRepository.findByUsuarioId(usuario.getId()).orElseThrow();
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("estudiante", getEstudianteLogueado());
        return "estudiante/dashboard";
    }

    @GetMapping("/resultados")
    public String resultados(Model model) {
        Estudiante estudiante = getEstudianteLogueado();
        model.addAttribute("estudiante", estudiante);
        
        Optional<ResultadoPrueba> resultadoOpt = resultadoPruebaRepository.findByEstudianteId(estudiante.getId());
        
        if (resultadoOpt.isPresent()) {
            model.addAttribute("tieneResultados", true);
            model.addAttribute("resultado", resultadoOpt.get());
        } else {
            model.addAttribute("tieneResultados", false);
        }
        
        return "estudiante/resultados";
    }

    @GetMapping("/beneficios")
    public String beneficios(Model model) {
        Estudiante estudiante = getEstudianteLogueado();
        model.addAttribute("estudiante", estudiante);
        
        List<BeneficioAsignado> beneficios = beneficioAsignadoRepository.findByEstudianteId(estudiante.getId());
        model.addAttribute("beneficios", beneficios);
        
        return "estudiante/beneficios";
    }

    @GetMapping("/pagos")
    public String vistaPagos(Model model) {
        model.addAttribute("estudiante", getEstudianteLogueado());
        return "estudiante/pagos";
    }

    @PostMapping("/pagos/subir-comprobante")
    public String subirComprobante(@RequestParam("archivoPdf") MultipartFile archivoPdf) {
        // Simulamos la subida del PDF
        if (archivoPdf.isEmpty() || !archivoPdf.getOriginalFilename().endsWith(".pdf")) {
            return "redirect:/estudiante/pagos?error";
        }
        return "redirect:/estudiante/pagos?exito";
    }
}