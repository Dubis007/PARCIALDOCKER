package com.pruebastyt.util;

import com.pruebastyt.dto.ResultadoExcelDTO;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {

    // Validación más flexible: Solo verificamos la extensión
    public static boolean hasExcelFormat(MultipartFile file) {
        String filename = file.getOriginalFilename();
        return filename != null && (filename.toLowerCase().endsWith(".xlsx") || filename.toLowerCase().endsWith(".xls"));
    }

    public static List<ResultadoExcelDTO> excelToResultados(InputStream is) {
        try {
            // WorkbookFactory soporta tanto .xls como .xlsx automáticamente
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            List<ResultadoExcelDTO> resultados = new ArrayList<>();

            // DataFormatter lee cualquier celda como String de forma segura
            DataFormatter formatter = new DataFormatter();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                
                // Saltamos la fila 0 (Cabeceras)
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                // Si la fila está completamente vacía, la saltamos
                if (isRowEmpty(currentRow)) {
                    continue;
                }

                ResultadoExcelDTO dto = new ResultadoExcelDTO();
                
                // Columna 0: Documento (Leído de forma segura)
                String doc = formatter.formatCellValue(currentRow.getCell(0)).trim();
                dto.setDocumentoEstudiante(doc);

                // Columna 1: Tipo Prueba
                String tipo = formatter.formatCellValue(currentRow.getCell(1)).trim();
                dto.setTipoPrueba(tipo.isEmpty() ? "SABER_PRO" : tipo);

                // Columnas 2 a 7: Puntajes (Convertimos de String a Integer de forma segura)
                dto.setPuntajeGlobal(parseInteger(formatter.formatCellValue(currentRow.getCell(2))));
                dto.setPuntajeLecturaCritica(parseInteger(formatter.formatCellValue(currentRow.getCell(3))));
                dto.setPuntajeRazonamientoCuantitativo(parseInteger(formatter.formatCellValue(currentRow.getCell(4))));
                dto.setPuntajeCompetenciasCiudadanas(parseInteger(formatter.formatCellValue(currentRow.getCell(5))));
                dto.setPuntajeComunicacionEscrita(parseInteger(formatter.formatCellValue(currentRow.getCell(6))));
                dto.setPuntajeIngles(parseInteger(formatter.formatCellValue(currentRow.getCell(7))));

                resultados.add(dto);
            }
            workbook.close();
            return resultados;
        } catch (Exception e) {
            System.err.println("❌ ERROR CRÍTICO LEYENDO EXCEL: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al procesar el archivo Excel: " + e.getMessage());
        }
    }

    // Método auxiliar para convertir a entero sin que colapse si hay decimales o espacios
    private static Integer parseInteger(String value) {
        try {
            if (value == null || value.trim().isEmpty()) return 0;
            // Si Excel lo manda como "200.0", le quitamos el decimal
            if (value.contains(".")) {
                value = value.substring(0, value.indexOf("."));
            }
            // Si Excel lo manda con comas "200,0"
            if (value.contains(",")) {
                value = value.substring(0, value.indexOf(","));
            }
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // Método auxiliar para detectar filas vacías al final del Excel
    private static boolean isRowEmpty(Row row) {
        if (row == null) return true;
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }
}