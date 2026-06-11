package com.pruebastyt.reports;

import com.pruebastyt.model.BeneficioAsignado;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

public class BeneficiosExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<BeneficioAsignado> listaBeneficios;

    public BeneficiosExcelExporter(List<BeneficioAsignado> listaBeneficios) {
        this.listaBeneficios = listaBeneficios;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Beneficios Asignados");
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        style.setFont(font);

        createCell(row, 0, "Documento", style);
        createCell(row, 1, "Código", style);
        createCell(row, 2, "Estudiante", style);
        createCell(row, 3, "Facultad", style);
        createCell(row, 4, "Beneficio Otorgado", style);
        createCell(row, 5, "% Descuento", style);
        createCell(row, 6, "Fecha Asignación", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(11);
        style.setFont(font);

        for (BeneficioAsignado ba : listaBeneficios) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, ba.getEstudiante().getDocumento(), style);
            createCell(row, columnCount++, ba.getEstudiante().getCodigo(), style);
            createCell(row, columnCount++, ba.getEstudiante().getNombres() + " " + ba.getEstudiante().getApellidos(), style);
            createCell(row, columnCount++, ba.getEstudiante().getFacultad().getNombre(), style);
            createCell(row, columnCount++, ba.getBeneficio().getNombre(), style);
            createCell(row, columnCount++, ba.getBeneficio().getPorcentajeDescuento() + "%", style);
            createCell(row, columnCount++, ba.getFechaAsignacion().toString(), style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}