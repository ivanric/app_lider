package app.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
@RequestMapping("/participantes")
public class ParticipanteController {

    @RequestMapping("/gestion") 
    public String alamat(Model model) {
    	System.out.println("LLEGO SOC");
        return "participantes/gestion";

    }
  
}
