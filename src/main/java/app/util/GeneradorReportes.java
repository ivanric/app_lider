package app.util;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;

public class GeneradorReportes {
//
//    // Método para cargar y registrar fuentes
    private void cargarFuentes() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        registrarFuente(ge, "/fonts/eras-itc-bold.ttf");
        registrarFuente(ge, "/fonts/Montserrat/static/Montserrat-Regular.ttf");
        registrarFuente(ge, "/fonts/Montserrat/static/Montserrat-Bold.ttf");
    }
//
    private void registrarFuente(GraphicsEnvironment ge, String fontPath) {
        try (InputStream fontStream = getClass().getResourceAsStream(fontPath)) {
            if (fontStream == null) {
                System.err.println("⚠ No se encontró la fuente: " + fontPath);
                return;
            }
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            ge.registerFont(customFont);
            System.out.println("✅ Fuente registrada correctamente: " + fontPath);
        } catch (Exception e) {
            System.err.println("❌ Error al registrar la fuente: " + fontPath);
            e.printStackTrace();
        }
    }

    // Método principal para generar el reporte
//    public void generarReporte(HttpServletResponse response, JasperReport reporte, String format, Map<String, Object> parameters, Connection connection, String nameReport, String onOffLine) throws JRException, SQLException, IOException {
//        // Cargar fuentes antes de generar el reporte
//        cargarFuentes();
//
//        byte[] reporteGenerado = generarReporte2(reporte, format, parameters, connection);
//
//        try (ServletOutputStream out = response.getOutputStream()) {
//            String mime = getMimeType(format);
//            String extension = getFileExtension(format);
//
//            response.setContentType(mime);
//            response.setHeader("Content-Disposition", onOffLine + "; filename=" + nameReport + extension);
//            response.setContentLength(reporteGenerado.length);
//
//            out.write(reporteGenerado);
//        }
//    }
//
//    // Método para generar el reporte en diferentes formatos
//    public byte[] generarReporte2(JasperReport reporte, String format, Map<String, Object> parameters, Connection connection) throws JRException, IOException {
//        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, connection);
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//        switch (format.toLowerCase()) {
//            case "pdf":
//                JasperExportManager.exportReportToPdfStream(jasperPrint, out);
//                break;
//            case "xls":
//                exportToXls(jasperPrint, out);
//                break;
//            case "html":
//                exportToHtml(jasperPrint, out);
//                break;
//            case "rtf":
//                exportToRtf(jasperPrint, out);
//                break;
//            case "txt":
//                exportToTxt(jasperPrint, out);
//                break;
//            default:
//                throw new IllegalArgumentException("Formato no soportado: " + format);
//        }
//
//        return out.toByteArray();
//    }
//
//    // Métodos para exportar en diferentes formatos
//    private void exportToXls(JasperPrint jasperPrint, ByteArrayOutputStream out) throws JRException {
//        JRXlsxExporter exporter = new JRXlsxExporter();
//        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
//        exporter.exportReport();
//    }
//
//    private void exportToHtml(JasperPrint jasperPrint, ByteArrayOutputStream out) throws JRException {
//        HtmlExporter exporter = new HtmlExporter();
//        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//        exporter.setExporterOutput(new SimpleHtmlExporterOutput(out));
//        exporter.exportReport();
//    }
//
//    private void exportToRtf(JasperPrint jasperPrint, ByteArrayOutputStream out) throws JRException {
//        JRRtfExporter exporter = new JRRtfExporter();
//        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//        exporter.setExporterOutput(new SimpleWriterExporterOutput(out));
//        exporter.exportReport();
//    }
//
//    private void exportToTxt(JasperPrint jasperPrint, ByteArrayOutputStream out) throws JRException {
//        JRTextExporter exporter = new JRTextExporter();
//        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//        exporter.setExporterOutput(new SimpleWriterExporterOutput(out));
//        exporter.exportReport();
//    }
//
//    // Métodos auxiliares para obtener MIME y extensiones de archivo
//    private String getMimeType(String format) {
//        switch (format.toLowerCase()) {
//            case "xls": return "application/vnd.ms-excel";
//            case "rtf": return "application/rtf";
//            case "html": return "text/html";
//            case "txt": return "text/plain";
//            case "pdf":
//            default: return "application/pdf";
//        }
//    }
//
//    private String getFileExtension(String format) {
//        switch (format.toLowerCase()) {
//            case "xls": return ".xls";
//            case "rtf": return ".rtf";
//            case "html": return ".html";
//            case "txt": return ".txt";
//            case "pdf":
//            default: return ".pdf";
//        }
//    }
    
    public void generarReporte(HttpServletResponse response, JasperReport reporte, String format, Map<String, Object> parameters, Connection connection, String nameReport, String onOffLine) throws JRException, SQLException, IOException {
        // Cargar la fuente personalizada antes de generar el reporte
        cargarFuentes();

        byte[] reporteGenerado = generarReporte2(reporte, format, parameters, connection);

        try (ServletOutputStream out = response.getOutputStream()) {
            String mime = getMimeType(format);
            String extension = getFileExtension(format);

            response.setContentType(mime);
            response.setHeader("Content-disposition", onOffLine + "; filename=" + nameReport + extension);
            response.setContentLength(reporteGenerado.length);

            out.write(reporteGenerado, 0, reporteGenerado.length);
        }
    }

    // Método para generar el reporte en el formato solicitado
    public byte[] generarReporte2(JasperReport reporte, String format, Map<String, Object> parameters, Connection connection) throws JRException, IOException {
        // Cargar la fuente personalizada antes de generar el reporte
        cargarFuentes();

        // Rellenar el reporte con los datos de la conexión y parámetros
        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, connection);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // Exportar según el formato
        switch (format.toLowerCase()) {
            case "pdf":
                JasperExportManager.exportReportToPdfStream(jasperPrint, out);
                break;
            case "xls":
                exportToXls(jasperPrint, out);
                break;
            case "html":
                exportToHtml(jasperPrint, out);
                break;
            case "rtf":
                exportToRtf(jasperPrint, out);
                break;
            case "txt":
                exportToTxt(jasperPrint, out, parameters);
                break;
            default:
                throw new IllegalArgumentException("Formato no soportado: " + format);
        }

        return out.toByteArray();
    }

    // Métodos de exportación para cada formato
    private void exportToXls(JasperPrint jasperPrint, ByteArrayOutputStream out) throws JRException {
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
        SimpleXlsxReportConfiguration config = new SimpleXlsxReportConfiguration();
        config.setOnePagePerSheet(false);
        config.setDetectCellType(true);
        exporter.setConfiguration(config);
        exporter.exportReport();
    }

    private void exportToHtml(JasperPrint jasperPrint, ByteArrayOutputStream out) throws JRException {
        HtmlExporter exporter = new HtmlExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleHtmlExporterOutput(out));
        SimpleHtmlReportConfiguration config = new SimpleHtmlReportConfiguration();
        exporter.setConfiguration(config);
        exporter.exportReport();
    }

    private void exportToRtf(JasperPrint jasperPrint, ByteArrayOutputStream out) throws JRException {
        JRRtfExporter exporter = new JRRtfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleWriterExporterOutput(out));
        exporter.exportReport();
    }

    private void exportToTxt(JasperPrint jasperPrint, ByteArrayOutputStream out, Map<String, Object> parameters) throws JRException {
        JRTextExporter exporter = new JRTextExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleWriterExporterOutput(out));

        SimpleTextReportConfiguration config = new SimpleTextReportConfiguration();
        if (parameters != null) {
            config.setCharWidth(Float.parseFloat(parameters.getOrDefault("TXT_CHARACTER_WIDTH", "5.0").toString()));
            config.setCharHeight(Float.parseFloat(parameters.getOrDefault("TXT_CHARACTER_HEIGHT", "10.0").toString()));
        }

        exporter.setConfiguration(config);
        exporter.exportReport();
    }

    // Obtener el tipo MIME según el formato
    private String getMimeType(String format) {
        switch (format.toLowerCase()) {
            case "xls":
                return "application/vnd.ms-excel";
            case "rtf":
                return "application/rtf";
            case "html":
                return "text/html";
            case "txt":
                return "text/plain";
            case "pdf":
            default:
                return "application/pdf";
        }
    }

    // Obtener la extensión del archivo según el formato
    private String getFileExtension(String format) {
        switch (format.toLowerCase()) {
            case "xls":
                return ".xls";
            case "rtf":
                return ".rtf";
            case "html":
                return ".html";
            case "txt":
                return ".txt";
            case "pdf":
            default:
                return ".pdf";
        }
    }
}
