package app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/anios")
public class AnioController {

    @RequestMapping("/gestion")
    public String alamat(Model model) {

        return "anios/gestion";

    }
}
