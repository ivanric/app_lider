package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import app.config.RutaConfig;




@Controller
@RequestMapping("/expositor")
public class ExpositorController {


    @Autowired
    private RutaConfig rutaConfig;
    
    @RequestMapping("/gestion") 
    public String alamat(Model model) {
    	System.out.println("LLEGO SOC");
//    	model.addAttribute("rutaBase", rutaConfig.getRutaBase());
        return "expositores/gestion";

    }
//    @RequestMapping("estadosocio/{id}")
//	public String generateImageQRCode(@PathVariable("id") String id,Model modelo) {
//		System.out.println("DATO:"+id); 
//		modelo.addAttribute("codigosocio", id);	
//		return "socios/estadosocio";
//	}
//    @RequestMapping("/empresas/{id}")
// 	public String showCatalogoSocio(@PathVariable("id") String id,Model modelo) {
// 		System.out.println("DATO:"+id); 
// 		modelo.addAttribute("codigosocio", id);	
// 		return "socios/catalogosocio";
// 	}
}
