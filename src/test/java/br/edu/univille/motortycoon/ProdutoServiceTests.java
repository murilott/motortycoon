package br.edu.univille.motortycoon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.univille.motortycoon.entity.Equipamento;
import br.edu.univille.motortycoon.repository.EquipamentoRepository;
import br.edu.univille.motortycoon.service.EquipamentoService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTests {
    @Mock
    private EquipamentoRepository equipamentoRepository;

    @InjectMocks
    private EquipamentoService equipamentoService;

    @BeforeEach
    void setUp() {
        equipamentoRepository.deleteAll();  // Limpa o banco de dados antes de cada teste
    }

    // REFATORADO - MAGIC NUMBER

    @Test
    public void testSalvarProduto() {
        // Define os valores constantes utilizados
        final int ESTOQUE_ESPERADO = 10;
        final float CUSTO_ESPERADO = 50;
        final String NOME_ESPERADO =  "Capacete M";
        final int QUANTIDADE_ESPERADA_DE_CHAMADA = 1;

        // Cria o produto
        Equipamento equipamento = new Equipamento();
        equipamento.setNome(NOME_ESPERADO);
        equipamento.setEstoque(ESTOQUE_ESPERADO);
        equipamento.setCusto(CUSTO_ESPERADO);

        // Simula a persistência do objeto 'equipamento' no repositório, retornando o próprio objeto 'equipamento'
        when(equipamentoRepository.save(equipamento)).thenReturn(equipamento);

        // Salva o produto no banco de dados
        Equipamento equipamentoSalvo = equipamentoService.salvar(equipamento);

        // Verifica se o método save foi chamado uma vez no mock e está salvo no repositório
        verify(equipamentoRepository, times(QUANTIDADE_ESPERADA_DE_CHAMADA)).save(equipamento);

        assertEquals(NOME_ESPERADO, equipamentoSalvo.getNome());
        assertEquals(ESTOQUE_ESPERADO, equipamentoSalvo.getEstoque());
        assertEquals(CUSTO_ESPERADO, equipamentoSalvo.getCusto());
    }

    // SMELL

    @Test
    public void SmellTestSalvarProduto() {
        // Cria o produto
        Equipamento equipamento = new Equipamento();
        equipamento.setNome("Capacete");
        equipamento.setEstoque(10);
        equipamento.setCusto(50);

        // Simula a persistência do objeto 'equipamento' no repositório, retornando o próprio objeto 'equipamento'
        when(equipamentoRepository.save(equipamento)).thenReturn(equipamento);

        // Salva o produto no banco de dados
        Equipamento equipamentoSalvo = equipamentoService.salvar(equipamento);

        // Verifica se o método save foi chamado uma vez no mock e está salvo no repositório
        verify(equipamentoRepository, times(1)).save(equipamento);

        assertEquals("Capacete", equipamentoSalvo.getNome());
        assertEquals(10, equipamentoSalvo.getEstoque());
        assertEquals(50, equipamentoSalvo.getCusto());
    }
}
