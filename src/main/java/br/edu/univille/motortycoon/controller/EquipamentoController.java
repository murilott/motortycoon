package br.edu.univille.capacete.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.univille.capacete.entity.Equipamento;
import br.edu.univille.capacete.service.EquipamentoService;

@Controller
@RequestMapping("/equipamento")
public class EquipamentoController {
    @Autowired
    private EquipamentoService service;

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
        return mv;
    }

    @PostMapping
    @RequestMapping("/salvar")
    public ModelAndView salvarNovo(@Valid @ModelAttribute("elemento") Equipamento equipamento, BindingResult bindingResult){
        try{
            if ( bindingResult.hasErrors() ) {
                var mv = new ModelAndView("equipamento/novo");
                mv.addObject("elemento", equipamento);
                return mv;
            }
            
            service.salvar(equipamento);
            return new ModelAndView("redirect:/equipamento");
        }catch (Exception e){
            var mv = new ModelAndView("equipamento/novo");
            mv.addObject("elemento", equipamento);
            mv.addObject("erro", e.getMessage());
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
