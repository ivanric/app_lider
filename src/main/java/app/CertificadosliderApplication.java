package app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import app.entity.DepartamentoEntity;
import app.entity.InstitucionEntity;
import app.entity.PaisEntity;
import app.entity.PersonaEntity;
import app.entity.ProfesionEntity;
import app.entity.ProvinciaEntity;
import app.entity.RolEntity;
import app.entity.UsuarioEntity;
import app.repository.DepartamentoRepository;
import app.repository.InstitucionRepository;
import app.repository.PaisRepository;
import app.repository.PersonaRepository;
import app.repository.ProfesionRepository;
import app.repository.ProvinciaRepository;
import app.repository.RolRepository;
import app.repository.UsuarioRepository;
import app.util.URIS;

@SpringBootApplication
public class CertificadosliderApplication extends SpringBootServletInitializer {
	@Autowired
    private ProfesionRepository profesionRepository;
    
    @Autowired
    private PaisRepository paisRepository;
    
    @Autowired
    private DepartamentoRepository departamentoRepository;
    
    @Autowired
    private ProvinciaRepository provinciaRepository;
    
    @Autowired
    private InstitucionRepository institucionRepository;
    
    @Autowired
    private RolRepository rolRepository;
    
    @Autowired
    private PersonaRepository personaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    
	public static void main(String[] args) {
		SpringApplication.run(CertificadosliderApplication.class, args);
		BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
		String password= bCryptPasswordEncoder.encode("LiderAcademia1234");//encrypt password
		System.out.println(password);//$2a$10$nrjVExWrFoz7NGFYL82ix.O0zMFreoAsaLSGtpfhruCuq1BcY.gDm
		/*
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
			
		}*/
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
	
	@Bean
    public ApplicationRunner initializer() {
        return args -> {
            // Crear o verificar la carpeta raíz en Google Drive
//            try {
//                String rootFolderId = googleDriveService.getOrCreateRootFolder();
//                System.out.println("Root folder ID: " + rootFolderId);
//            } catch (Exception e) {
//                e.printStackTrace();
//                System.out.println("Error al crear o obtener la carpeta raíz: " + e.getMessage());
//            }
            
            ProfesionEntity profesionEntity = new ProfesionEntity(1, "LICENCIATURA EN ADMINISTRACION DE EMPRESAS", 1);
            ProfesionEntity profesionsave = profesionRepository.save(profesionEntity);
            
            // Agregar país Bolivia
            PaisEntity paisBolivia = paisRepository.findById(1).orElseGet(() -> {
                PaisEntity nuevoPais = new PaisEntity(1, "BOLIVIA", 1);
                return paisRepository.save(nuevoPais);
            });
            
//            DepartamentoEntity departamentoEntity = new DepartamentoEntity(1,"TJA", "TARIJA", 1, paissave);
//            DepartamentoEntity departamentosave = departamentoRepository.save(departamentoEntity);
            
            // Lista de departamentos de Bolivia
            List<DepartamentoEntity> departamentos = List.of(
                new DepartamentoEntity(1, "TJA", "TARIJA", 1, paisBolivia),
                new DepartamentoEntity(2, "CB", "COCHABAMBA", 1, paisBolivia),
                new DepartamentoEntity(3, "SC", "SANTA CRUZ", 1, paisBolivia),
                new DepartamentoEntity(4, "OR", "ORURO", 1, paisBolivia),
                new DepartamentoEntity(5, "PT", "POTOSI", 1, paisBolivia),
                new DepartamentoEntity(6, "CH", "CHUQUISACA", 1, paisBolivia),
                new DepartamentoEntity(7, "BN", "BENI", 1, paisBolivia),
                new DepartamentoEntity(8, "PD", "PANDO", 1, paisBolivia),
                new DepartamentoEntity(9, "LP", "LA PAZ", 1, paisBolivia)
            );
            // Guardar los departamentos si no existen
            for (DepartamentoEntity departamento : departamentos) {
                departamentoRepository.findById(departamento.getId()).orElseGet(() -> departamentoRepository.save(departamento));
            }
            
            System.out.println("✅ Departamentos de Bolivia agregados correctamente.");

            // Obtener el departamento de Tarija
            DepartamentoEntity tarija = departamentoRepository.findById(1)
                    .orElseThrow(() -> new RuntimeException("Departamento de Tarija no encontrado"));

//            ProvinciaEntity proviniciaEntity = new ProvinciaEntity(1, "CERCADO", 1, tarija); 
//            ProvinciaEntity provinciasave = provinciaRepository.save(proviniciaEntity);
            // Crear provincia en Tarija si no existe
            ProvinciaEntity cercado = provinciaRepository.findById(1).orElseGet(() -> {
                ProvinciaEntity nuevaProvincia = new ProvinciaEntity(1, "CERCADO", 1, tarija);
                return provinciaRepository.save(nuevaProvincia);
            });

            
            
            System.out.println("**************INIT PRE INSTI****");
            InstitucionEntity institucionEntitybd = null;
            try {
                institucionEntitybd = institucionRepository.findById(1).get();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
                institucionEntitybd = null;
            }
            
            System.out.println("**************INIT INSTITUCION:" + institucionEntitybd);
            
            if (institucionEntitybd == null) {
                System.out.println("****************AGREGANDO INSTITUCION NUEVA");
                InstitucionEntity institucionEntity = new InstitucionEntity(1, "123456789105", "LIDER ACADEMIA", "LIDER", null, "javiermed11@gmail.com", "direccion", "70212292", null, "lideracademia.com", null, 1, cercado);
                institucionRepository.save(institucionEntity);
            }
            
            Collection<RolEntity> rolesarray = new ArrayList<>();
            RolEntity rolEntity = new RolEntity(1,"ROLE_ADMIN", 1); 
            RolEntity rolsave = rolRepository.save(rolEntity); 
            rolesarray.add(rolsave);
            
            PersonaEntity persona = new PersonaEntity(1, "7205095","TJA","SERGIO JAVIER","MEDINA SEGOVIA",33,null,"m","javermed11@gmail.com", 70212295,null, 1);
            personaRepository.save(persona);
            
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String password = bCryptPasswordEncoder.encode("LiderAcademia1234"); // Encrypt password
            System.out.println(password); //$2a$10$Pe2oBHzkQVDf5ubi5ubThe0xAIimT00ogdXiAQKmX3g4.4W/LG.pm
            
            UsuarioEntity usuarioEntity = new UsuarioEntity(1, "javiermed11@gmail.com", password, 1,null,null, rolesarray, persona);
            usuarioRepository.save(usuarioEntity);
            
            System.out.println("Usuarios agregados a la base de datos.");
        };
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CertificadosliderApplication	.class);
    }

}
