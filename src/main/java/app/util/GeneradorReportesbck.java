package app.util;
/*
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.ByteArrayOutputStream;
import java.io.File;
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

public class GeneradorReportesbck {

    // Método para cargar múltiples fuentes dinámicamente
//	private void cargarFuentes(List<String> fontPaths) {
//	    if (fontPaths != null) {
//	        for (String fontPath : fontPaths) {
//	            try (InputStream fontStream = getClass().getClassLoader().getResourceAsStream(fontPath)) {
//	                if (fontStream == null) {
//	                    System.err.println("⚠ No se encontró la fuente en el classpath: " + fontPath);
//	                    continue;
//	                }
//
//	                Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
//	                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//	                ge.registerFont(customFont);
//	                System.out.println("✅ Fuente registrada: " + fontPath);
//
//	            } catch (Exception e) {
//	                System.err.println("❌ Error al cargar la fuente: " + fontPath);
//	                e.printStackTrace();
//	            }
//	        }
//	    } else {
//	        System.err.println("⚠ No se proporcionaron rutas de fuentes.");
//	    }
//	}
//    private void cargarFuente() {
//        try {
//            // Cargar la fuente TTF
//            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/eras-itc-bold.ttf"));
//            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//            ge.registerFont(customFont);
//        } catch (Exception e) {
//            e.printStackTrace();  // Manejo de excepciones
//        }
//    }
//	private void cargarFuentes() {
//	    try {
//	        // Cargar las fuentes TTF
//	        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/eras-itc-bold.ttf"));
//	        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//	        ge.registerFont(customFont);
//
//	        // Registrar también la fuente "Montserrat-Regular"
//	        Font montserratRegular = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/Montserrat/static/Montserrat-Regular.ttf"));
//	        ge.registerFont(montserratRegular);
//	        
//	        // Registrar también la fuente "Montserrat-Regular"
//	        Font montserratBold= Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/Montserrat/static/Montserrat-Bold.ttf"));
//	        ge.registerFont(montserratBold);
//	        
//	        System.out.println("Fuente registrada: fonts/Montserrat/static/Montserrat-Regular.ttf");
//
//	    } catch (Exception e) {
//	        e.printStackTrace();  // Manejo de excepciones
//	        System.out.println(e.getMessage());
//	    }
//	}

	 private void cargarFuentes() {
	        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	        registrarFuente(ge, "/fonts/eras-itc-bold.ttf");
	        registrarFuente(ge, "/fonts/Montserrat/static/Montserrat-Regular.ttf");
	        registrarFuente(ge, "/fonts/Montserrat/static/Montserrat-Bold.ttf");
	    }

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
    public void generarReporte(HttpServletResponse response, JasperReport reporte, String format, Map<String, Object> parameters, Connection connection, String nameReport, String onOffLine, List<String> fontPaths) throws JRException, SQLException, IOException {
        // Cargar las fuentes antes de generar el reporte
       

        byte[] reporteGenerado = generarReporte2(reporte, format, parameters, connection, fontPaths);

        try (ServletOutputStream out = response.getOutputStream()) {
            String mime = getMimeType(format);
            String extension = getFileExtension(format);

            response.setContentType(mime);
            response.setHeader("Content-disposition", onOffLine + "; filename=" + nameReport + extension);
            response.setContentLength(reporteGenerado.length);

            out.write(reporteGenerado, 0, reporteGenerado.length);
        }
    }

    // Método para generar el reporte con fuentes personalizadas
    public byte[] generarReporte2(JasperReport reporte, String format, Map<String, Object> parameters, Connection connection, List<String> fontPaths) throws JRException, IOException {
        // Cargar fuentes personalizadas
        cargarFuentes();

        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, connection);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

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

    private void exportToXls(JasperPrint jasperPrint, ByteArrayOutputStream out) throws JRException {
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
        exporter.exportReport();
    }

    private void exportToHtml(JasperPrint jasperPrint, ByteArrayOutputStream out) throws JRException {
        HtmlExporter exporter = new HtmlExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleHtmlExporterOutput(out));
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
        exporter.exportReport();
    }

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
*/