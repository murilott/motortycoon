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

import br.edu.univille.motortycoon.entity.Categoria;
import br.edu.univille.motortycoon.repository.CategoriaRepository;
import br.edu.univille.motortycoon.service.CategoriaService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTests {
    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @BeforeEach
    void setUp() {
        categoriaRepository.deleteAll();  // Limpa o banco de dados antes de cada teste
    }

    // REFATORADO - EMPTY TEST

    @Test
    public void testSalvarCategoria() {
        Categoria categoria = new Categoria();
        categoria.setNome("Capacete");

        // Simula a persistência do objeto 'categoria' no repositório, retornando o próprio objeto 'categoria'
        when(categoriaRepository.save(categoria)).thenReturn(categoria);

        // Salva a categoria no banco de dados
        Categoria categoriaSalvo = categoriaService.salvar(categoria);

        // Verifica se o método save foi chamado uma vez no mock e está salvo no repositório
        verify(categoriaRepository, times(1)).save(categoria);

        // Verifica se a categoria salvou corretamente
        assertEquals(categoria, categoriaSalvo);
    }

    // SMELL

    @Test
    public void SmellTestSalvarCategoria() {
        // Cria a categoria
        // Categoria categoria = new Categoria();
        // categoria.setNome("Capacete");

        // Simula a persistência do objeto 'categoria' no repositório, retornando o próprio objeto 'categoria'
        // when(categoriaRepository.save(categoria)).thenReturn(categoria);

        // Categoria categoriaSalvo = categoriaService.salvar(categoria);
    }
}
