package com.pruebastyt.service.impl;

import com.pruebastyt.model.Beneficio;
import com.pruebastyt.model.BeneficioAsignado;
import com.pruebastyt.model.ResultadoPrueba;
import com.pruebastyt.repository.BeneficioAsignadoRepository;
import com.pruebastyt.repository.BeneficioRepository;
import com.pruebastyt.service.BeneficioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BeneficioServiceImpl implements BeneficioService {

    @Autowired
    private BeneficioRepository beneficioRepository;

    @Autowired
    private BeneficioAsignadoRepository beneficioAsignadoRepository;

    @Override
    public void evaluarYAsignarBeneficios(ResultadoPrueba resultado) {
        Integer puntaje = resultado.getPuntajeGlobal();
        String nombreBeneficio = null;

        // Motor de Reglas (Escala de puntajes)
        if (puntaje >= 200) {
            nombreBeneficio = "Beca Excelencia";
        } else if (puntaje >= 180) {
            nombreBeneficio = "Mención Honorífica";
        } else if (puntaje >= 160) {
            nombreBeneficio = "Reconocimiento Académico";
        }

        // Si aplica a un beneficio, lo asignamos
        if (nombreBeneficio != null) {
            Optional<Beneficio> optBeneficio = beneficioRepository.findByNombre(nombreBeneficio);
            
            if (optBeneficio.isPresent()) {
                Beneficio beneficio = optBeneficio.get();
                
                // Verificamos si ya lo tiene asignado para no duplicar
                Optional<BeneficioAsignado> existente = beneficioAsignadoRepository
                        .findByEstudianteIdAndBeneficioId(resultado.getEstudiante().getId(), beneficio.getId());
                
                if (existente.isEmpty()) {
                    BeneficioAsignado asignacion = new BeneficioAsignado();
                    asignacion.setEstudiante(resultado.getEstudiante());
                    asignacion.setBeneficio(beneficio);
                    asignacion.setFechaAsignacion(LocalDate.now());
                    asignacion.setEstado("APROBADO");
                    
                    beneficioAsignadoRepository.save(asignacion);
                    System.out.println("🏆 BENEFICIO ASIGNADO: " + nombreBeneficio + " al estudiante " + resultado.getEstudiante().getDocumento());
                }
            }
        }
    }
}