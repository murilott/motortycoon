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

import br.edu.univille.motortycoon.entity.Pagamento;
import br.edu.univille.motortycoon.service.PagamentoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/pagamento")
public class PagamentoController {
    @Autowired
    private PagamentoService service;

    @GetMapping
    public ModelAndView index(){
        var mv = new ModelAndView("pagamento/index");
        mv.addObject("lista", service.obterTodos());
        return mv;
    }

    @GetMapping
    @RequestMapping("/novo")
    public ModelAndView novo(){
        var mv = new ModelAndView("pagamento/novo");
        mv.addObject("elemento", new Pagamento());
        return mv;
    }

    @PostMapping
    @RequestMapping("/salvar")
    public ModelAndView salvarNovo(@Valid @ModelAttribute("elemento") Pagamento pagamento, BindingResult bindingResult){
        try{
            if ( bindingResult.hasErrors() ) {
                var mv = new ModelAndView("pagamento/novo");
                mv.addObject("elemento", pagamento);
                return mv;
            }
            
            service.salvar(pagamento);
            return new ModelAndView("redirect:/pagamento");
        }catch (Exception e){
            var mv = new ModelAndView("pagamento/novo");
            mv.addObject("elemento", pagamento);
            mv.addObject("erro", e.getMessage());
            return mv;
        }
    }

    @GetMapping
    @RequestMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") long id){
        var mv = new ModelAndView("pagamento/novo");
        var opt = service.obterPeloId(id);
        
        if(opt.isPresent()) {
            mv.addObject("elemento", opt.get());
            return mv;
        }

        return new ModelAndView("redirect:/pagamento");
    }

    @GetMapping
    @RequestMapping("/{id}/excluir")
    public ModelAndView excluir(@PathVariable long id){
        var mv = new ModelAndView("pagamento/editar");
        var opt = service.obterPeloId(id);

        if(opt.isPresent()) {
            service.excluir(opt.get());
        }

        return new ModelAndView("redirect:/pagamento");
    }
}
