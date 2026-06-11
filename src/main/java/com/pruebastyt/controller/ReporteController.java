package com.pruebastyt.controller;

import com.pruebastyt.model.BeneficioAsignado;
import com.pruebastyt.repository.BeneficioAsignadoRepository;
import com.pruebastyt.reports.BeneficiosExcelExporter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/coordinador/reportes")
public class ReporteController {

    @Autowired
    private BeneficioAsignadoRepository beneficioAsignadoRepository;

    @GetMapping
    public String vistaReportes() {
        return "coordinador/reportes";
    }

    @GetMapping("/exportar/beneficios")
    public void exportarBeneficiosExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=beneficios_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        // Obtenemos todos los beneficios asignados de la base de datos
        List<BeneficioAsignado> listaBeneficios = beneficioAsignadoRepository.findAll();

        // Generamos y enviamos el Excel
        BeneficiosExcelExporter excelExporter = new BeneficiosExcelExporter(listaBeneficios);
        excelExporter.export(response);
    }
}