package br.edu.univille.motortycoon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.univille.motortycoon.entity.Equipamento;
import br.edu.univille.motortycoon.service.CategoriaService;
import br.edu.univille.motortycoon.service.EquipamentoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/equipamento")
public class EquipamentoController {
    @Autowired
    private EquipamentoService service;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ModelAndView index(){
        var mv = new ModelAndView("equipamento/index");
        mv.addObject("lista", service.obterTodos());
        return mv;
    }

    @GetMapping
    @RequestMapping("/novo")
    public ModelAndView novo(){
        var mv = new ModelAndView("equipamento/novo");
        mv.addObject("elemento", new Equipamento());
        mv.addObject("listaCategoria", categoriaService.obterTodos());
        return mv;
    }
    
    @PostMapping
    @RequestMapping("/salvar")
    public ModelAndView salvarNovo(@Valid @ModelAttribute("elemento") Equipamento equipamento, BindingResult bindingResult){
        try{
            if ( bindingResult.hasErrors() ) {
                var mv = new ModelAndView("equipamento/novo");
                mv.addObject("elemento", equipamento);
                mv.addObject("listaCategoria", categoriaService.obterTodos());
                return mv;
            }
            
            service.salvar(equipamento);
            return new ModelAndView("redirect:/equipamento");
        }catch (Exception e){
            var mv = new ModelAndView("equipamento/novo");
            mv.addObject("elemento", equipamento);
            mv.addObject("erro", e.getMessage());
            mv.addObject("listaCategoria", categoriaService.obterTodos());
            return mv;
        }
    }

    @GetMapping
    @RequestMapping("/{id}")
    public ModelAndView editar(@PathVariable long id){
        var mv = new ModelAndView("equipamento/editar");
        var opt = service.obterPeloId(id);
        
        if(opt.isPresent()) {
            mv.addObject("elemento", opt.get());
            return mv;
        }

        return new ModelAndView("redirect:/equipamento");
    }

    @GetMapping
    @RequestMapping("/{id}/excluir")
    public ModelAndView excluir(@PathVariable long id){
        var mv = new ModelAndView("equipamento/editar");
        var opt = service.obterPeloId(id);

        if(opt.isPresent()) {
            service.excluir(opt.get());
        }

        return new ModelAndView("redirect:/equipamento");
    }
}
