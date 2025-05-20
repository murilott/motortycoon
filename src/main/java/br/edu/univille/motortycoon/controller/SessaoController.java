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

    @GetMapping()
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView("sessao/login");
        return mv;
    }

    @GetMapping("/registrar")
    public ModelAndView register() {
        ModelAndView mv = new ModelAndView("sessao/register");
        mv.addObject("elemento", new Usuario());
        return mv;
    }

    // Processar registro de usuário
    @PostMapping("/register")
    public ModelAndView processRegister(@ModelAttribute Usuario usuario) {
        ModelAndView mv = new ModelAndView("register");

        if (usuarioRepository.findByUsername(usuario.getUsername()) != null) {
            mv.addObject("error", "Usuário já existe!");
            return mv;
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRole("USER");
        userRepository.save(usuario);

        mv.setViewName("redirect:/usuario/login");
        return mv;
    }

    // Página inicial após login
    @GetMapping("/home")
    public ModelAndView home(Principal principal) {
        ModelAndView mav = new ModelAndView("home");
        User usuario = userRepository.findByUsername(principal.getName());
        mav.addObject("username", usuario.getUsername());
        mav.addObject("role", usuario.getRole());
        return mav;
    }
}
