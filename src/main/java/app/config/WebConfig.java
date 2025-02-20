//package app.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")  // Permite todas las rutas
//                .allowedOrigins("https://lideracademia.com")  // Especifica los dominios permitidos
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")  // Métodos permitidos
//                .allowedHeaders("*")  // Permite todos los encabezados
//                .allowCredentials(true)  // Permite el uso de cookies o autenticación
//                .maxAge(3600);  // Duración de la configuración CORS (en segundos)
//    }
//}