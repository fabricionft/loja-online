package com.futshop.futshop.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RotasController {

    @GetMapping("/publica")
    public ModelAndView publica(){
        return new ModelAndView("menuGestao.html");
    }

    @GetMapping("/segunda")
    public ModelAndView segunda(){
        return new ModelAndView("gestaoProdutos.html");
    }
}
