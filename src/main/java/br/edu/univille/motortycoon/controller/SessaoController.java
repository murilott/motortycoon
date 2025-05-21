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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping()
    public ModelAndView index() {
        var mv = new ModelAndView("sessao/login");
        return mv;
    }

    @PostMapping("/entrar")
    public ModelAndView login(@RequestParam String email, @RequestParam String senha, RedirectAttributes redirectAttributes) {
        
        try {            
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, senha)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ModelAndView("redirect:/home"); // Redireciona para a página inicial após login
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Usuário ou senha inválidos!");
            return new ModelAndView("redirect:/home"); // Volta para a tela de login com mensagem de erro
        }
    }

    @GetMapping("/registrar")
    public ModelAndView registrar() {
        var mv = new ModelAndView("sessao/registrar");
        mv.addObject("elemento", new Usuario());
        return mv;
    }

    @PostMapping("/registrar")
    public ModelAndView processRegister(@ModelAttribute("usuario") Usuario usuario) {
        var mv = new ModelAndView("sessao/registrar");

        if (usuarioRepository.findByEmail(usuario.getEmail()) != null) {
            mv.addObject("error", "Usuário já existe!");
            return mv;
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        // usuario.setCargo(Cargo.USER);
        usuarioRepository.save(usuario);

        mv.setViewName("redirect:/usuario/login");
        return mv;
    }

    // @GetMapping("/home")
    // public ModelAndView home(Principal principal) {
    //     ModelAndView mav = new ModelAndView("home");
    //     User usuario = userRepository.findByUsername(principal.getName());
    //     mav.addObject("username", usuario.getUsername());
    //     mav.addObject("role", usuario.getRole());
    //     return mav;
    // }
}
