package br.edu.univille.motortycoon.controller;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.univille.motortycoon.entity.Cargo;
import br.edu.univille.motortycoon.entity.Carrinho;
import br.edu.univille.motortycoon.entity.Usuario;
import br.edu.univille.motortycoon.repository.UsuarioRepository;
import br.edu.univille.motortycoon.service.CarrinhoService;
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
    private CarrinhoService carrinhoService;

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping()
    public ModelAndView index() {
        var mv = new ModelAndView("sessao/login");
        return mv;
    }

    @GetMapping("/registrar")
    public ModelAndView registrar() {
        var mv = new ModelAndView("sessao/registrar");
        mv.addObject("elemento", new Usuario());
        mv.addObject("listaPagamento", pagamentoService.obterTodos());
        return mv;
    }

    @GetMapping("/redefinir")
    public ModelAndView redefinir() {
        var mv = new ModelAndView("sessao/redefinir");
        return mv;
    }

    @PostMapping("/registrar")
    public ModelAndView processRegister(@ModelAttribute("elemento") @Valid Usuario usuario, @Valid @RequestParam(name = "cargo", required = false) Boolean admin, BindingResult bindingResult) {
        try {
            if ( bindingResult.hasErrors() ) {
                for (ObjectError error : bindingResult.getAllErrors()) {
            System.out.println("AAAAAAAAAAAAAAAAAAA Erro de validação: " + error.getDefaultMessage());
        }

                var mv = new ModelAndView("sessao/registrar");
                usuario.setSenha(null);
                mv.addObject("elemento", usuario);
                mv.addObject("listaPagamento", pagamentoService.obterTodos());
                
                return mv;
            }
            
            if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
                var mv = new ModelAndView("sessao/registrar");
                usuario.setSenha(null);
                mv.addObject("elemento", usuario);
                mv.addObject("erro", "E-mail já está em uso!");
                return mv;
            }

            List<String> roles = usuario.getCargos();
    
            if (roles == null) {
                roles = new ArrayList<>();
            }
            
            if (!roles.contains("ROLE_USER")) {
                roles.add("ROLE_USER");
                // roles.add("ROLE_ADMIN");
            }

            if ((admin != null) && !roles.contains("ROLE_ADMIN")) {
                roles.add("ROLE_ADMIN");
            }

            usuario.setCargos(roles);  // Atualiza o usuário com a lista de papéis
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

            Carrinho carrinho = new Carrinho();
            carrinho = carrinhoService.salvar(carrinho);
            
            usuario.setCarrinhoAtual(carrinho);
            usuario = usuarioService.salvar(usuario);
            
            carrinho.setUsuario(usuario);
            carrinhoService.salvar(carrinho);

            return new ModelAndView("redirect:/login?registered"); 
        } catch (Exception e){
            var mv = new ModelAndView("sessao/registrar");
            mv.addObject("listaPagamento", pagamentoService.obterTodos());
            mv.addObject("erro", e.getMessage());
            return mv;
        }
    
    }

    @PostMapping("/redefinir")
    public ModelAndView redefinirSenha(@RequestParam("email") String email, @RequestParam("senha") String senha, RedirectAttributes redirectAttributes) {
        try {
            Optional<Usuario> usuarioOpt = usuarioService.obterPeloEmail(email); //.orElse(null)

            if (usuarioOpt.isEmpty()) {
                return new ModelAndView("redirect:/login/redefinir?email");
            }

            Usuario usuario = usuarioOpt.get();
            usuario.setSenha(passwordEncoder.encode(senha));
            usuarioRepository.save(usuario);

            return new ModelAndView("redirect:/login?redefined"); 
        } catch (Exception e){
            var mv = new ModelAndView("sessao/redefinir");
            mv.addObject("erro", e.getMessage());
            return mv;
        }
    }
}
