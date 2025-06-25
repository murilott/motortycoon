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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.univille.motortycoon.entity.Carrinho;
import br.edu.univille.motortycoon.entity.Equipamento;
import br.edu.univille.motortycoon.entity.ItemCarrinho;
import br.edu.univille.motortycoon.entity.Usuario;
import br.edu.univille.motortycoon.service.CarrinhoService;
import br.edu.univille.motortycoon.service.EquipamentoService;
import br.edu.univille.motortycoon.service.ItemCarrinhoService;
import br.edu.univille.motortycoon.service.UsuarioService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {
    @Autowired
    private CarrinhoService service;

    @Autowired
    private ItemCarrinhoService itemCarrinhoService;

    @Autowired
    private EquipamentoService equipamentoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ModelAndView index(Principal principal){
        String email = principal.getName();
        Usuario usuario = usuarioService.obterPeloEmail(email).orElse(null);
        Carrinho carrinho = usuario.getCarrinhoAtual();

        var mv = new ModelAndView("carrinho/index");
        mv.addObject("carrinho", carrinho);
        return mv;
    }

    @GetMapping("/api/carrinho/itens")
    @ResponseBody
    public int getNumeroItensCarrinho(Principal principal) {
        if (principal == null) {
            // Retorna 0 itens se o usuário não estiver logado
            return 0;
        }
        
        String email = principal.getName();
        Usuario usuario = usuarioService.obterPeloEmail(email).orElse(null);
        Carrinho carrinho = usuario.getCarrinhoAtual();

        return carrinho.getItens().size();
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

            if ( item.getQuantidade() <= 0 ) {
                long id = item.getProduto().getId();
                var mv = new ModelAndView("redirect:/equipamento/view/" + id + "?quantidadeMenor");
                mv.addObject("elemento", item);
                mv.addObject("itemCarrinho", item);
                return mv;
            }

            if ( item.getQuantidade() > item.getProduto().getEstoque() ) {
                long id = item.getProduto().getId();
                var mv = new ModelAndView("redirect:/equipamento/view/" + id + "?quantidadeEstoque");
                mv.addObject("elemento", item);
                mv.addObject("itemCarrinho", item);
                return mv;
            }

            if ( bindingResult.hasErrors() ) {
                long id = item.getProduto().getId();
                var mv = new ModelAndView("redirect:/equipamento/view/" + id);
                mv.addObject("elemento", item);
                mv.addObject("itemCarrinho", item);
                return mv;
            }
            
            item = itemCarrinhoService.salvar(item);

            carrinho.getItens().add(item);

            item.setCarrinho(carrinho);
            item.setCusto(item.calcularCusto());

            carrinho.setCustoTotal(carrinho.calcularCustoTotal());

            Equipamento produto = equipamentoService.obterPeloId(item.getProduto().getId()).get();
            // produto.setEstoque(produto.getEstoque() - item.getQuantidade());

            System.out.println("PRODUTO ESTOQUWEEE: " + produto);
            System.out.println("quantidadeeeeee: " + item.getQuantidade());

            if (produto.getEstoque() == item.getQuantidade()) {
                // Se a quantidade for igual ao estoque, o estoque deve ser zerado após a compra
                produto.setEstoque(0);
            } else {
                // Caso contrário, subtrai a quantidade do estoque
                produto.setEstoque(produto.getEstoque() - item.getQuantidade());
            }

            System.out.println("DEPOIS DO ESTOQUEEEEE");
            
            equipamentoService.salvar(produto);
            
            System.out.println("DEPOIS DE SALVAR RQUIPAMENTOOOO");
            service.salvar(carrinho);
            System.out.println("DEPOIS DE SALVAR CARRINHOOOOO");
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
    @RequestMapping("/remover/{id}")
    public ModelAndView remover(@PathVariable("id") long id, Principal principal) {
        String email = principal.getName();
        Usuario usuario = usuarioService.obterPeloEmail(email).orElse(null);
        Carrinho carrinho = usuario.getCarrinhoAtual();

        var opt = itemCarrinhoService.obterPeloId(id);
    
        if(opt.isPresent()) {
            ItemCarrinho item = opt.get();

            carrinho.getItens().remove(item);
            // item.setCarrinho(null);
            carrinho.setCustoTotal(carrinho.calcularCustoTotal());

            Equipamento produto = equipamentoService.obterPeloId(item.getProduto().getId()).get();
            produto.setEstoque(produto.getEstoque() + item.getQuantidade());
            equipamentoService.salvar(produto);

            itemCarrinhoService.excluir(item);
            service.salvar(carrinho);

            return new ModelAndView("redirect:/carrinho?sucesso");
        }

        return new ModelAndView("redirect:/carrinho?erro");
    }

    @PostMapping
    @RequestMapping("/finalizar")
    public ModelAndView finalizarCompra(Principal principal) {
        try {
            String email = principal.getName();
            Usuario usuario = usuarioService.obterPeloEmail(email).orElse(null);
            Carrinho carrinho = usuario.getCarrinhoAtual();

            if ( carrinho.getItens().isEmpty() ) {
                var mv = new ModelAndView("redirect:/carrinho?vazio");
                return mv;
            }
            
            usuario.getHistorico().add(carrinho);
            
            usuario.setCarrinhoAtual(null);
            
            Carrinho NovoCarrinho = new Carrinho();
            NovoCarrinho.setUsuario(usuario);
            NovoCarrinho = service.salvar(NovoCarrinho);
            
            usuario.setCarrinhoAtual(NovoCarrinho);
            usuario = usuarioService.salvar(usuario);
            
            service.salvar(NovoCarrinho);

            return new ModelAndView("redirect:/carrinho?finalizar");
        } catch (Exception e){
            var mv = new ModelAndView("carrinho/index");
            mv.addObject("erro", e.getMessage());
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
