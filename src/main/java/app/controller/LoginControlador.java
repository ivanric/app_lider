package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

//import app.service.UsuarioServicio;

@Controller
public class LoginControlador {
	
//	@Autowired
//	private UsuarioServicio usuarioServicio;
	
	@GetMapping("/login")
	public String iniciarSesion(){
		return "login";
	}
	@GetMapping("/") 
	public String verPaginaInicio(Model model){
//		model.addAttribute("usuarios",usuarioServicio.listarUsuarios());
		return "inicio";
	}
	
	@GetMapping("/form/register")
	public String formregisterpublic(){
		return "/form/publicregister";
	}
    @RequestMapping("certificado/{code}")
	public String generateImageQRCode(@PathVariable("code") String code,Model modelo) {
//		log.info("BookResourceImpl - generateImageQRCode");
		System.out.println("code:"+code); 
		modelo.addAttribute("code", code);	
		return "inscritos/validacioncertificado";
	}
}
