package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import app.config.RutaConfig;




@Controller
@RequestMapping("/provincia")
public class ProvinciaController {


    
    @RequestMapping("/gestion") 
    public String alamat(Model model) {
    	System.out.println("LLEGO SOC");
//    	model.addAttribute("rutaBase", rutaConfig.getRutaBase());
        return "provincias/gestion";

    }

}
