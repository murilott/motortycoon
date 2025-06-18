package br.edu.univille.motortycoon.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.edu.univille.motortycoon.entity.Carrinho;
import br.edu.univille.motortycoon.entity.Usuario;
import br.edu.univille.motortycoon.service.UsuarioService;


@ControllerAdvice
public class GlobalControllerAdvice {
    @Autowired
    private UsuarioService usuarioService;

    // @ModelAttribute("tamCarrinho")
    //     public int obterTamanhoCarrinho(Principal principal) {
    //         String email = principal.getName();
    //         Usuario usuario = usuarioService.obterPeloEmail(email).orElse(null);
    //         Carrinho carrinho = usuario.getCarrinhoAtual();

    //         return carrinho.obterTamanhoCarrinho();
    // }
}