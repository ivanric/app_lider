package app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
@RequestMapping("/evento")
public class EventoController {

    @RequestMapping("/gestion") 
    public String alamat(Model model) {
    	System.out.println("LLEGO CONTROLLER");
        return "eventos/gestion";

    }
    @RequestMapping("/code/{code}")
	public String eventocodigo(@PathVariable("code") String id,Model modelo) {
//		log.info("BookResourceImpl - generateImageQRCode");
		System.out.println("DATO:"+id); 
		modelo.addAttribute("codigoeventoencript", id);	
		return "eventos/eventocode";
	}
}
