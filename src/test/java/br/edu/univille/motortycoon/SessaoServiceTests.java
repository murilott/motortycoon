package br.edu.univille.motortycoon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.edu.univille.motortycoon.entity.Categoria;
import br.edu.univille.motortycoon.entity.Equipamento;
import br.edu.univille.motortycoon.entity.Usuario;
import br.edu.univille.motortycoon.repository.CategoriaRepository;
import br.edu.univille.motortycoon.repository.EquipamentoRepository;
import br.edu.univille.motortycoon.repository.UsuarioRepository;
import br.edu.univille.motortycoon.service.CategoriaService;
import br.edu.univille.motortycoon.service.EquipamentoService;
import br.edu.univille.motortycoon.service.UsuarioService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SessaoServiceTests {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    Usuario usuario;

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();
        
        usuario = new Usuario();
        usuario.setNomeCompleto("Murilo");
        usuario.setEmail("murilo@gmail.com");
        usuario.setSenha("123");
        usuario.setCargos(new ArrayList<String>());
    }

    // REFATORADO - CONDITIONAL TEST LOGIC

    @Test
    public void testAtribuirCargoUser() {        
        usuario.getCargos().add("ROLE_USER");

        assertTrue(usuario.getCargos().contains("ROLE_USER"));
        assertFalse(usuario.getCargos().contains("ROLE_ADMIN"));
    }

    @Test
    public void testAtribuirCargoAdmin() {
        usuario.getCargos().add("ROLE_USER");
        usuario.getCargos().add("ROLE_ADMIN");

        assertTrue(usuario.getCargos().contains("ROLE_USER"));
        assertTrue(usuario.getCargos().contains("ROLE_ADMIN"));
    }

    // SMELL

    @Test
    public void SmellTestAtribuirCargo() {
        boolean admin = false;
        
        if (!admin) {
            usuario.getCargos().add("ROLE_USER");
            assertTrue(usuario.getCargos().contains("ROLE_USER"));
        } else {
            usuario.getCargos().add("ROLE_ADMIN");
            assertTrue(usuario.getCargos().contains("ROLE_ADMIN"));
        }
    }
}
