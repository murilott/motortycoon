package br.edu.univille.motortycoon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.univille.motortycoon.entity.Categoria;
import br.edu.univille.motortycoon.entity.Equipamento;
import br.edu.univille.motortycoon.entity.ItemCarrinho;
import br.edu.univille.motortycoon.repository.CategoriaRepository;
import br.edu.univille.motortycoon.repository.EquipamentoRepository;
import br.edu.univille.motortycoon.repository.ItemCarrinhoRepository;
import br.edu.univille.motortycoon.service.CategoriaService;
import br.edu.univille.motortycoon.service.EquipamentoService;
import br.edu.univille.motortycoon.service.ItemCarrinhoService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ItemCarrinhoServiceTests {
    @Mock
    private ItemCarrinhoRepository itemCarrinhoRepository;
    @InjectMocks
    private ItemCarrinhoService itemCarrinhoService;

    @Mock
    private EquipamentoRepository equipamentoRepository;
    @InjectMocks
    private EquipamentoService equipamentoService;

    @Mock
    private CategoriaRepository categoriaRepository;
    @InjectMocks
    private CategoriaService categoriaService;

    @BeforeEach
    void setUp() {
        itemCarrinhoRepository.deleteAll();  // Limpa o banco de dados antes de cada teste
    }

    // REFATORADO - LAZY TEST

    @Test
    public void testCalcularCustoItemCarrinho100() {
        Categoria categoria = new Categoria();
        categoria.setNome("Capacete");

        Equipamento equipamento = new Equipamento();
        equipamento.setNome("Capacete X");
        equipamento.setCusto(50);
        equipamento.setCategoria(categoria);

        ItemCarrinho item = new ItemCarrinho();
        item.setProduto(equipamento);
        item.setQuantidade(2);
        item.setCusto(item.calcularCusto());

        ItemCarrinho item2 = new ItemCarrinho();
        item2.setProduto(equipamento);
        item2.setQuantidade(5);
        item2.setCusto(item2.calcularCusto());

        double custoEsperado = 50 * 2;
        double custoEsperado2 = 50 * 5;
        
        assertEquals(custoEsperado, item.getCusto());
        assertEquals(custoEsperado2, item2.getCusto());
    }

    // SMELL

    @Test
    public void SmellTestCalcularCustoItemCarrinho1() {
        Categoria categoria = new Categoria();
        categoria.setNome("Capacete");

        Equipamento equipamento = new Equipamento();
        equipamento.setNome("Capacete X");
        equipamento.setCusto(50);
        equipamento.setCategoria(categoria);

        ItemCarrinho item = new ItemCarrinho();
        item.setProduto(equipamento);
        item.setQuantidade(2);
        item.setCusto(item.calcularCusto());

        double custoEsperado = 50 * 2;
        
        assertEquals(custoEsperado, item.getCusto());
    }

    @Test
    public void SmellTestCalcularCustoItemCarrinho2() {
        Categoria categoria = new Categoria();
        categoria.setNome("Capacete");

        Equipamento equipamento = new Equipamento();
        equipamento.setNome("Capacete X");
        equipamento.setCusto(50);
        equipamento.setCategoria(categoria);

        ItemCarrinho item = new ItemCarrinho();
        item.setProduto(equipamento);
        item.setQuantidade(2);
        item.setCusto(item.calcularCusto());

        double custoEsperado = 50 * 2;
        
        assertEquals(custoEsperado, item.getCusto());
    }
}
