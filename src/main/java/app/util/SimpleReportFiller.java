package app.util;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleReportFiller {

    private String reportFileName;
    private JasperReport jasperReport;
    private JasperPrint jasperPrint;
    
    @Autowired
    private DataSource dataSource;
    
    private Map<String, Object> parameters;
    private JasperReport jasperArchive;

    public SimpleReportFiller() {
        parameters = new HashMap<>();
    }

    public void prepareReport() {
        compileReport();
        fillReport();
    }

    public void compileReport() {
        try {
            // Carga el archivo .jrxml y compílalo a .jasper
            InputStream reportStream = getClass().getResourceAsStream("/".concat(reportFileName));
            if (reportStream != null) {
                jasperReport = JasperCompileManager.compileReport(reportStream);
                // Guarda el archivo compilado .jasper
                JRSaver.saveObject(jasperReport, reportFileName.replace(".jrxml", ".jasper"));
            } else {
                throw new JRException("No se encontró el archivo ".concat(reportFileName));
            }
        } catch (JRException ex) {
            Logger.getLogger(SimpleReportFiller.class.getName()).log(Level.SEVERE, "Error al compilar el reporte", ex);
        }
    }

    public void setReporte(URL url) throws JRException {
        // Carga un archivo .jasper precompilado desde una URL
        jasperArchive = (JasperReport) JRLoader.loadObject(url);
    }

    public void fillReport() {
        try (Connection connection = dataSource.getConnection()) {
            if (jasperReport != null) {
                // Rellenar el reporte con la conexión de base de datos
                jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            } else if (jasperArchive != null) {
                // Si no se compiló un reporte, intenta cargar un archivo jasper preexistente
                jasperPrint = JasperFillManager.fillReport(jasperArchive, parameters, connection);
            } else {
                throw new JRException("No se encontró un reporte para llenar.");
            }
        } catch (JRException | SQLException ex) {
            Logger.getLogger(SimpleReportFiller.class.getName()).log(Level.SEVERE, "Error al llenar el reporte", ex);
        }
    }

    // Métodos setters y getters

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public String getReportFileName() {
        return reportFileName;
    }

    public void setReportFileName(String reportFileName) {
        this.reportFileName = reportFileName;
    }

    public JasperPrint getJasperPrint() {
        return jasperPrint;
    }
}
