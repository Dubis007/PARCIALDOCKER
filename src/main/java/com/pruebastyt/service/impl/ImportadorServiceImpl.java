package com.pruebastyt.service.impl;

import com.pruebastyt.dto.ResultadoExcelDTO;
import com.pruebastyt.model.Estudiante;
import com.pruebastyt.model.ImportacionExcel;
import com.pruebastyt.model.ResultadoPrueba;
import com.pruebastyt.model.Usuario;
import com.pruebastyt.repository.EstudianteRepository;
import com.pruebastyt.repository.ImportacionExcelRepository;
import com.pruebastyt.repository.ResultadoPruebaRepository;
import com.pruebastyt.service.BeneficioService;
import com.pruebastyt.service.ImportadorService;
import com.pruebastyt.util.ExcelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ImportadorServiceImpl implements ImportadorService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private ResultadoPruebaRepository resultadoPruebaRepository;

    @Autowired
    private ImportacionExcelRepository importacionExcelRepository;

    @Autowired
    private BeneficioService beneficioService;

    @Override
    @Transactional
    public void procesarExcel(MultipartFile file, Usuario coordinador) {
        try {
            List<ResultadoExcelDTO> registrosExcel = ExcelHelper.excelToResultados(file.getInputStream());
            
            int procesados = 0;
            int errores = 0;

            System.out.println("=================================================");
            System.out.println("INICIANDO PROCESAMIENTO DE EXCEL: " + file.getOriginalFilename());
            System.out.println("=================================================");

            for (ResultadoExcelDTO dto : registrosExcel) {
                String docExcel = dto.getDocumentoEstudiante();
                System.out.print("🔍 Buscando documento: [" + docExcel + "] -> ");

                Optional<Estudiante> optEstudiante = estudianteRepository.findByDocumento(docExcel);
                
                if (optEstudiante.isPresent()) {
                    Estudiante estudiante = optEstudiante.get();
                    
                    // 1. Guardar o actualizar el Resultado
                    ResultadoPrueba resultado = resultadoPruebaRepository.findByEstudianteId(estudiante.getId())
                            .orElse(new ResultadoPrueba());
                    
                    resultado.setEstudiante(estudiante);
                    resultado.setTipoPrueba(dto.getTipoPrueba());
                    resultado.setPuntajeGlobal(dto.getPuntajeGlobal());
                    resultado.setPuntajeLecturaCritica(dto.getPuntajeLecturaCritica());
                    resultado.setPuntajeRazonamientoCuantitativo(dto.getPuntajeRazonamientoCuantitativo());
                    resultado.setPuntajeCompetenciasCiudadanas(dto.getPuntajeCompetenciasCiudadanas());
                    resultado.setPuntajeComunicacionEscrita(dto.getPuntajeComunicacionEscrita());
                    resultado.setPuntajeIngles(dto.getPuntajeIngles());
                    
                    // Guardamos el resultado y capturamos la entidad guardada
                    ResultadoPrueba resultadoGuardado = resultadoPruebaRepository.save(resultado);
                    System.out.println("✅ Puntaje guardado: " + dto.getPuntajeGlobal());

                    // 2. Disparar el Motor de Beneficios INMEDIATAMENTE
                    beneficioService.evaluarYAsignarBeneficios(resultadoGuardado);
                    
                    procesados++;
                } else {
                    System.out.println("❌ ERROR: Estudiante no existe en la BD.");
                    errores++;
                }
            }

            // 3. Guardar Auditoría
            ImportacionExcel auditoria = new ImportacionExcel();
            auditoria.setNombreArchivo(file.getOriginalFilename());
            auditoria.setFechaImportacion(LocalDateTime.now());
            auditoria.setTotalRegistros(registrosExcel.size());
            auditoria.setRegistrosProcesados(procesados);
            auditoria.setRegistrosConError(errores);
            auditoria.setUsuario(coordinador);
            
            importacionExcelRepository.save(auditoria);
            
            System.out.println("=================================================");
            System.out.println("RESUMEN: Procesados=" + procesados + " | Errores=" + errores);
            System.out.println("=================================================");

        } catch (Exception e) {
            System.err.println("❌ ERROR CRÍTICO EN IMPORTACIÓN: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Fallo al almacenar datos del Excel: " + e.getMessage());
        }
    }
}