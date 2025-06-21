package br.edu.univille.motortycoon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
        final int ESTOQUE_PADRAO = 10;
        final float CUSTO_PADRAO = 50;
        final int QUANTIDADE_ESPERADA_DE_CHAMADA = 1;

        // Cria o produto
        Equipamento equipamento = new Equipamento();
        equipamento.setNome("Capacete M");
        equipamento.setEstoque(ESTOQUE_PADRAO);
        equipamento.setCusto(CUSTO_PADRAO);

        // Simula a persistência do objeto 'equipamento' no repositório, retornando o próprio objeto 'equipamento'
        when(equipamentoRepository.save(equipamento)).thenReturn(equipamento);

        // Salva o produto no banco de dados
        Equipamento equipamentoSalvo = equipamentoService.salvar(equipamento);

        // Verifica se o método save foi chamado uma vez no mock e está salvo no repositório
        verify(equipamentoRepository, times(QUANTIDADE_ESPERADA_DE_CHAMADA)).save(equipamento);

        // Verifica se o equipamento salvou corretamente
        assertEquals(equipamento, equipamentoSalvo);
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

        // Verifica se o equipamento salvou corretamente
        assertEquals(equipamento, equipamentoSalvo);
    }
}
