package br.edu.univille.motortycoon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.univille.motortycoon.service.EquipamentoService;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private EquipamentoService service;

    @GetMapping
    public ModelAndView index(){
        var mv = new ModelAndView("home/index");
        mv.addObject("lista", service.obterTodos());
        return mv;
    }
}
