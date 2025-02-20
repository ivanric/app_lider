package app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Permite todas las rutas
            .allowedOrigins("*")  // Permite solicitudes desde cualquier origen
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")  // Permite todos los métodos
            .allowedHeaders("*")  // Permite todos los encabezados
            .allowCredentials(false)  // Si no deseas permitir credenciales (cookies, autenticación HTTP)
            .maxAge(3600);  // Cache de la configuración CORS durante 1 hora (en segundos)
    }
}
