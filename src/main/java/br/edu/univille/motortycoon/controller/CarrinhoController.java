package br.edu.univille.motortycoon.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.univille.motortycoon.entity.Carrinho;
import br.edu.univille.motortycoon.entity.Equipamento;
import br.edu.univille.motortycoon.entity.ItemCarrinho;
import br.edu.univille.motortycoon.entity.Usuario;
import br.edu.univille.motortycoon.service.CarrinhoService;
import br.edu.univille.motortycoon.service.EquipamentoService;
import br.edu.univille.motortycoon.service.UsuarioService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {
    @Autowired
    private CarrinhoService service;

    @Autowired
    private EquipamentoService equipamentoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ModelAndView index(){
        var mv = new ModelAndView("carrinho/index");
        mv.addObject("lista", service.obterTodos());
        return mv;
    }

    @GetMapping
    @RequestMapping("/novo")
    public ModelAndView novo(){
        var mv = new ModelAndView("carrinho/novo");
        mv.addObject("elemento", new Carrinho());
        return mv;
    }

    @PostMapping
    @RequestMapping("/adicionar")
    public ModelAndView adicionar(@Valid @ModelAttribute ItemCarrinho item, Principal principal, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        try{
            String email = principal.getName();
            Usuario usuario = usuarioService.obterPeloEmail(email).orElse(null);
            Carrinho carrinho = usuario.getCarrinhoAtual();

            // Equipamento equipamento = equipamentoService.obterPeloId(item.getProduto().getId()).orElseThrow();
            // Carrinho carrinho = service.obterPeloId(item.getCarrinho().getId()).orElse(null);

            // if (carrinho == null) {
            //     carrinho = new Carrinho();
            //     service.salvar(carrinho);
            // }
            
            if ( bindingResult.hasErrors() ) {
                long id = item.getProduto().getId();
                var mv = new ModelAndView("redirect:/equipamento/view/" + id);
                mv.addObject("elemento", item);
                return mv;
            }
            
            // item.setProduto(equipamento);
            // item.setCarrinho(carrinho);
            carrinho.getItens().add(item);
            item.setCarrinho(carrinho);

            service.salvar(carrinho);
            return new ModelAndView("redirect:/carrinho");
        }catch (Exception e){
            long id = item.getProduto().getId();
            var mv = new ModelAndView("redirect:/equipamento/view/" + id);
            redirectAttributes.addFlashAttribute("erro",  e.getMessage());
            redirectAttributes.addFlashAttribute("produto",  item.getProduto().getId());
            // mv.addObject("elemento", item);
            // mv.addObject("erro", e.getMessage());
            return mv;
        }
    }

    @PostMapping
    @RequestMapping("/salvar")
    public ModelAndView salvarNovo(@Valid @ModelAttribute("elemento") Carrinho carrinho, BindingResult bindingResult){
        try{
            if ( bindingResult.hasErrors() ) {
                var mv = new ModelAndView("carrinho/novo");
                mv.addObject("elemento", carrinho);
                return mv;
            }
            
            service.salvar(carrinho);
            return new ModelAndView("redirect:/carrinho");
        }catch (Exception e){
            var mv = new ModelAndView("carrinho/novo");
            mv.addObject("elemento", carrinho);
            mv.addObject("erro", e.getMessage());
            return mv;
        }
    }

    @GetMapping
    @RequestMapping("/{id}")
    public ModelAndView editar(@PathVariable long id){
        var mv = new ModelAndView("carrinho/editar");
        var opt = service.obterPeloId(id);
        
        if(opt.isPresent()) {
            mv.addObject("elemento", opt.get());
            return mv;
        }

        return new ModelAndView("redirect:/carrinho");
    }

    @GetMapping
    @RequestMapping("/{id}/excluir")
    public ModelAndView excluir(@PathVariable long id){
        var mv = new ModelAndView("carrinho/editar");
        var opt = service.obterPeloId(id);

        if(opt.isPresent()) {
            service.excluir(opt.get());
        }

        return new ModelAndView("redirect:/carrinho");
    }
}
