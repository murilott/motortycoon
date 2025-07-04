package br.edu.univille.motortycoon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.univille.motortycoon.entity.Usuario;
import br.edu.univille.motortycoon.service.PagamentoService;
import br.edu.univille.motortycoon.service.UsuarioService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioService service;

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ModelAndView index(){
        var mv = new ModelAndView("usuario/index");
        mv.addObject("lista", service.obterTodos());
        
        return mv;
    }

    @PostMapping
    @RequestMapping("/salvar")
    public ModelAndView salvarNovo(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult){
        try{
            if ( bindingResult.hasErrors() ) {
                var mv = new ModelAndView("usuario/novo");
                mv.addObject("listaPagamento", pagamentoService.obterTodos());
                mv.addObject("elemento", usuario);
                
                List<FieldError> errors = bindingResult.getFieldErrors();
                for (FieldError error : errors ) {
                    mv.addObject("erroBinding", error.getObjectName() + " - " + error.getDefaultMessage());
                }
                
                return mv;
            }
            
            if (!service.obterTodos().contains(usuario)) {
                usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));;
            }

            service.salvar(usuario);
            return new ModelAndView("redirect:/usuario");
        }catch (Exception e){
            var mv = new ModelAndView("usuario/novo");
            mv.addObject("listaPagamento", pagamentoService.obterTodos());
            mv.addObject("elemento", usuario);
            mv.addObject("erro", e.getMessage());
            return mv;
        }
    }

    @GetMapping
    @RequestMapping("/{id}")
    public ModelAndView editar(@PathVariable long id){
        var mv = new ModelAndView("usuario/editar");
        var opt = service.obterPeloId(id);
        
        if(opt.isPresent()) {
            mv.addObject("elemento", opt.get());
            return mv;
        }

        return new ModelAndView("redirect:/usuario");
    }

    @GetMapping
    @RequestMapping("/{id}/excluir")
    public ModelAndView excluir(@PathVariable long id){
        var mv = new ModelAndView("usuario/editar");
        var opt = service.obterPeloId(id);

        if(opt.isPresent()) {
            service.excluir(opt.get());
        }

        return new ModelAndView("redirect:/usuario");
    }

}
