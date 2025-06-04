package br.edu.univille.motortycoon.controller;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.univille.motortycoon.entity.Cargo;
import br.edu.univille.motortycoon.entity.Usuario;
import br.edu.univille.motortycoon.repository.UsuarioRepository;
import br.edu.univille.motortycoon.service.PagamentoService;
import br.edu.univille.motortycoon.service.UsuarioService;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/login")
public class SessaoController {

    @Autowired
    private UsuarioRepository usuarioRepository;
        
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // @Autowired
    // private AuthenticationManager authenticationManager;

    @GetMapping()
    public ModelAndView index() {
        var mv = new ModelAndView("sessao/login");
        return mv;
    }

    @GetMapping("/registrar")
    public ModelAndView registrar() {
        var mv = new ModelAndView("sessao/registrar");
        mv.addObject("elemento", new Usuario());
        return mv;
    }

    @PostMapping("/registrar")
    public ModelAndView processRegister(@ModelAttribute("elemento") Usuario usuario, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        try {
            if ( bindingResult.hasErrors() ) {
                var mv = new ModelAndView("sessao/registrar");
                mv.addObject("elemento", usuario);
                mv.addObject("erro", "Caiu no binding");

                List<FieldError> errors = bindingResult.getFieldErrors();
                for (FieldError error : errors ) {
                    mv.addObject("erroBinding", error.getObjectName() + " - " + error.getDefaultMessage());
                }

                return mv;
            }

            if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
                var mv = new ModelAndView("sessao/registrar");
                mv.addObject("elemento", usuario);
                mv.addObject("erro", "E-mail já está em uso!");
                return mv;
            }

            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            usuarioRepository.save(usuario);

            return new ModelAndView("redirect:/sessao/login?registered"); 
        } catch (Exception e){
            var mv = new ModelAndView("sessao/registrar");
            mv.addObject("elemento", usuario);
            mv.addObject("erro", e.getMessage());
            return mv;
        }
    }
}
