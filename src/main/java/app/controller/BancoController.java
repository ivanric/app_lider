package app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/banco")
public class BancoController {

    @RequestMapping("/gestion")
    public String alamat(Model model) {

        return "bancos/gestion";

    }
}
