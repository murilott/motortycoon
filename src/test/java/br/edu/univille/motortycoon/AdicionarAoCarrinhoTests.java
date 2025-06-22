package br.edu.univille.motortycoon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.edu.univille.motortycoon.entity.Carrinho;
import br.edu.univille.motortycoon.entity.Categoria;
import br.edu.univille.motortycoon.entity.Equipamento;
import br.edu.univille.motortycoon.entity.ItemCarrinho;
import br.edu.univille.motortycoon.repository.CarrinhoRepository;
import br.edu.univille.motortycoon.repository.CategoriaRepository;
import br.edu.univille.motortycoon.repository.EquipamentoRepository;
import br.edu.univille.motortycoon.repository.ItemCarrinhoRepository;
import br.edu.univille.motortycoon.service.CarrinhoService;
import br.edu.univille.motortycoon.service.CategoriaService;
import br.edu.univille.motortycoon.service.EquipamentoService;
import br.edu.univille.motortycoon.service.ItemCarrinhoService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdicionarAoCarrinhoTests {
    @Mock
    private EquipamentoRepository equipamentoRepository;

    @InjectMocks
    private EquipamentoService equipamentoService;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @Mock
    private ItemCarrinhoRepository itemCarrinhoRepository;

    @InjectMocks
    private ItemCarrinhoService itemCarrinhoService;

    @Mock
    private CarrinhoRepository carrinhoRepository;

    @InjectMocks
    private CarrinhoService carrinhoService;

    private Categoria categoria;
    private Equipamento equipamento;
    private ItemCarrinho item;
    private Carrinho carrinho;

    // REFATORADO - EAGER TEST

    @BeforeAll // Limpa o banco de dados antes dos testes
    void setUp() {
        equipamentoRepository.deleteAll();  
        categoriaRepository.deleteAll(); 
        itemCarrinhoRepository.deleteAll(); 
        carrinhoRepository.deleteAll(); 
    }

    @Test
    public void testAdicionarCategoria() {
        categoria = new Categoria();
        categoria.setNome("Capacete");
        
        when(categoriaRepository.save(categoria)).thenReturn(categoria);
        Categoria categoriaSalvo = categoriaService.salvar(categoria);
        verify(categoriaRepository, times(1)).save(categoria);
        assertEquals(categoria, categoriaSalvo);
    }

    @Test
    public void testAdicionarEquipamento() {
        equipamento = new Equipamento();
        equipamento.setNome("Capacete X");
        equipamento.setEstoque(10);
        equipamento.setCusto(50);
        equipamento.setCategoria(categoria);

        when(equipamentoRepository.save(equipamento)).thenReturn(equipamento);
        Equipamento equipamentoSalvo = equipamentoService.salvar(equipamento);
        verify(equipamentoRepository, times(1)).save(equipamento);
        assertEquals(equipamento, equipamentoSalvo);
    }

    @Test
    public void testAdicionarItemCarrinho() {
        item = new ItemCarrinho();
        item.setProduto(equipamento);
        item.setQuantidade(1);
        item.setCusto(item.calcularCusto());

        when(itemCarrinhoRepository.save(item)).thenReturn(item);
        ItemCarrinho itemSalvo = itemCarrinhoService.salvar(item);
        verify(itemCarrinhoRepository, times(1)).save(item);
        assertEquals(item, itemSalvo);
    }

    @Test
    public void testAdicionarCarrinho() {
        carrinho = new Carrinho();
        carrinho.setItens(new ArrayList<ItemCarrinho>());
        carrinho.getItens().add(item);
        
        when(carrinhoRepository.save(carrinho)).thenReturn(carrinho);
        Carrinho carrinhoSalvo = carrinhoService.salvar(carrinho);
        verify(carrinhoRepository, times(1)).save(carrinho);
        assertEquals(carrinho, carrinhoSalvo);
        
        assertEquals(1, carrinho.obterTamanhoCarrinho());
    }

    // SMELL

    // @BeforeEach // Limpa o banco de dados antes de cada teste
    // void SmellSetUp() {
    //     equipamentoRepository.deleteAll();  
    //     categoriaRepository.deleteAll(); 
    //     itemCarrinhoRepository.deleteAll(); 
    //     carrinhoRepository.deleteAll(); 
    // }

    // @Test
    // public void SmellTestFluxoAdicionarCarrinho() {
    //     Categoria categoria = new Categoria();
    //     categoria.setNome("Capacete");
        
    //     when(categoriaRepository.save(categoria)).thenReturn(categoria);
    //     Categoria categoriaSalvo = categoriaService.salvar(categoria);
    //     verify(categoriaRepository, times(1)).save(categoria);
    //     assertEquals(categoria, categoriaSalvo);

    //     Equipamento equipamento = new Equipamento();
    //     equipamento.setNome("Capacete X");
    //     equipamento.setEstoque(10);
    //     equipamento.setCusto(50);
    //     equipamento.setCategoria(categoria);

    //     when(equipamentoRepository.save(equipamento)).thenReturn(equipamento);
    //     Equipamento equipamentoSalvo = equipamentoService.salvar(equipamento);
    //     verify(equipamentoRepository, times(1)).save(equipamento);
    //     assertEquals(equipamento, equipamentoSalvo);
        
    //     ItemCarrinho item = new ItemCarrinho();
    //     item.setProduto(equipamento);
    //     item.setQuantidade(1);
    //     item.setCusto(item.calcularCusto());

    //     when(itemCarrinhoRepository.save(item)).thenReturn(item);
    //     ItemCarrinho itemSalvo = itemCarrinhoService.salvar(item);
    //     verify(itemCarrinhoRepository, times(1)).save(item);
    //     assertEquals(item, itemSalvo);
        
    //     Carrinho carrinho = new Carrinho();
    //     carrinho.setItens(new ArrayList<ItemCarrinho>());
    //     carrinho.getItens().add(item);
        
    //     when(carrinhoRepository.save(carrinho)).thenReturn(carrinho);
    //     Carrinho carrinhoSalvo = carrinhoService.salvar(carrinho);
    //     verify(carrinhoRepository, times(1)).save(carrinho);
    //     assertEquals(carrinho, carrinhoSalvo);
        
    //     assertEquals(1, carrinho.obterTamanhoCarrinho());

    //     // when(equipamentoRepository.save(equipamento)).thenReturn(equipamento);
    //     // when(equipamentoRepository.findById(1L)).thenReturn(java.util.Optional.of(equipamento));
    // }


}
