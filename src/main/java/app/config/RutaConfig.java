package app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;

@Configuration
public class RutaConfig {

    @Value("${ruta.base.linux}")
    private String rutaBaseLinux;

    @Value("${ruta.base.windows}")
    private String rutaBaseWindows;

    private String rutaBase;

    @PostConstruct
    public void init() {
        String os = System.getProperty("os.name");
        if (os.contains("Linux")) {
            this.rutaBase = rutaBaseLinux;
        } else if (os.contains("Windows")) {
            this.rutaBase = rutaBaseWindows;
        } else {
            throw new UnsupportedOperationException("Sistema operativo no soportado: " + os);
        }
    }

    public String getRutaBase() {
        return rutaBase;
    }
}
