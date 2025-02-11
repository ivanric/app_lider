package app.util;

import java.awt.GraphicsEnvironment;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
public class GeneradorReportes {

    // Método para cargar la fuente personalizada
    private void cargarFuente() {
        try {
            // Cargar la fuente TTF
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/eras-itc-bold.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (Exception e) {
            e.printStackTrace();  // Manejo de excepciones
        }
    }

    // Método principal para generar el reporte y enviarlo a la respuesta HTTP
    public void generarReporte(HttpServletResponse response, JasperReport reporte, String format, Map<String, Object> parameters, Connection connection, String nameReport, String onOffLine) throws JRException, SQLException, IOException {
        // Cargar la fuente personalizada antes de generar el reporte
        cargarFuente();

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
        cargarFuente();

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
