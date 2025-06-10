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

    @Test
    public void testSalvarProduto() {
        // Criar o produto
        Equipamento equipamento = new Equipamento();
        equipamento.setNome("Capacete");
        equipamento.setEstoque(10);
        equipamento.setCusto(50);

        when(equipamentoRepository.save(equipamento)).thenReturn(equipamento);

        // Salva o produto no banco de dados
        equipamento = equipamentoService.salvar(equipamento);

        // Verificar se o produto foi salvo corretamente
        assertNotNull(equipamento, "Equipamento não salvo");
        assertEquals("Capacete", equipamento.getNome(), "nome errado");
        assertEquals(10, equipamento.getEstoque());
        assertEquals(50, equipamento.getCusto());

        // Verificar se o método save foi chamado uma vez no mock e está salvo no repositório
        verify(equipamentoRepository, times(1)).save(equipamento);
    }
}
