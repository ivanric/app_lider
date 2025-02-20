package app.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;




@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(customUserDetailsService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}

	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.cors().disable() // Añadir esta línea
		.headers().disable()
		.csrf().disable()
//		.headers().frameOptions().sameOrigin()
		.authorizeRequests().antMatchers(
				"/registro**",
				"/js/**",
				"/css/**",
				"/img/**",
				"/logos/**",
				"/expositoreslogos/**",
				"/plantillas/**",
				"/afiches/**",
				"/form/**",
//				"/formregisterpublic/**",
				
				"/evento/code/**",//controlador para mostrar el evento
				"/RestCertificadosCursos/findByNrodocumento/**",
				"/RestCertificadosCursos/Imprimir_d1/**",
				"/RestEventos/findByCodeEncrypt/**",
				"/RestEventos/afiche/**", 
				"/RestMetodosPagos/logo/**", 
				"/RestMetodosPagos/logo_mp/**", 
				"/RestExpositores/logo/**", 
				"/RestExpositores/logo_expositor/**", 
				"/RestGradosAcademicos/**",
				"/RestProfesiones/**", 
				"/RestDepartamentos/**",
				"/RestProvincias/**" ,
				"/RestCursos/**",
				"/RestInscritos/guardarinscrito/**",
				"/RestInscritos/findEstudiante/**",
				"/RestParticipantes/logourl/**",
				"/certificado/**",
				"/logourl/**" // Añade esta línea
				).permitAll()
		 
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/login")
		.permitAll()
		.and()
		.logout()
		.invalidateHttpSession(true)
		.clearAuthentication(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login?logout")
		.permitAll();
	}
}
