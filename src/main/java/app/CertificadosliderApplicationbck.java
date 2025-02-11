package app;
/*
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import app.util.URIS;

@SpringBootApplication
public class CertificadosliderApplicationbck {

	public static void main(String[] args) {
		SpringApplication.run(CertificadosliderApplicationbck.class, args);
		BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
		String password= bCryptPasswordEncoder.encode("ivanric12");//encrypt password
		System.out.println(password);//$2a$10$nrjVExWrFoz7NGFYL82ix.O0zMFreoAsaLSGtpfhruCuq1BcY.gDm
		
		URIS uris=new URIS(); 
		System.out.println("INICIANDO APP");
		System.out.println("SISTEMA OPERATIVO:"+uris.checkOS());
		if ((uris.checkOS().contains("Linux"))) {
			try {
				System.out.println("DANDO PERMISOS A LA CARPETA DE ARCHIVOS");
				Process p = Runtime.getRuntime().exec("chmod -R 777 /opt/");
//				Process p1 = Runtime.getRuntime().exec("chown -R postgres:postgres /opt/");
				System.out.println("ruta_archivos linux :"+uris.obtenerRutaCarpetaRecursos(""));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println(e.getMessage());
			}

			
		}else if(uris.checkOS().contains("Windows")) {
			
		}
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
	    return new WebMvcConfigurer() {
	        @Override
	        public void addCorsMappings(CorsRegistry registry) {
	            registry.addMapping("/**").allowedOrigins("*"); // Permitir todos los orígenes
	        }
	    };
	}

}
*/