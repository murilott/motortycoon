package br.edu.univille.capacete.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.univille.capacete.entity.ItemCarrinho;
import br.edu.univille.capacete.service.CarrinhoService;

@Controller
@RequestMapping("/itemCarrinho")
public class CarrinhoController {
    @Autowired
    private CarrinhoService service;

    @GetMapping
    public ModelAndView index(){
        var mv = new ModelAndView("itemCarrinho/index");
        mv.addObject("lista", service.obterTodos());
        return mv;
    }

    @GetMapping
    @RequestMapping("/novo")
    public ModelAndView novo(){
        var mv = new ModelAndView("itemCarrinho/novo");
        mv.addObject("elemento", new ItemCarrinho());
        return mv;
    }

    @PostMapping
    @RequestMapping("/salvar")
    public ModelAndView salvarNovo(@Valid @ModelAttribute("elemento") ItemCarrinho itemCarrinho, BindingResult bindingResult){
        try{
            if ( bindingResult.hasErrors() ) {
                var mv = new ModelAndView("itemCarrinho/novo");
                mv.addObject("elemento", itemCarrinho);
                return mv;
            }
            
            service.salvar(itemCarrinho);
            return new ModelAndView("redirect:/itemCarrinho");
        }catch (Exception e){
            var mv = new ModelAndView("itemCarrinho/novo");
            mv.addObject("elemento", itemCarrinho);
            mv.addObject("erro", e.getMessage());
            return mv;
        }
    }

    @GetMapping
    @RequestMapping("/{id}")
    public ModelAndView editar(@PathVariable long id){
        var mv = new ModelAndView("itemCarrinho/editar");
        var opt = service.obterPeloId(id);
        
        if(opt.isPresent()) {
            mv.addObject("elemento", opt.get());
            return mv;
        }

        return new ModelAndView("redirect:/itemCarrinho");
    }

    @GetMapping
    @RequestMapping("/{id}/excluir")
    public ModelAndView excluir(@PathVariable long id){
        var mv = new ModelAndView("itemCarrinho/editar");
        var opt = service.obterPeloId(id);

        if(opt.isPresent()) {
            service.excluir(opt.get());
        }

        return new ModelAndView("redirect:/itemCarrinho");
    }
}
