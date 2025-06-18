package br.edu.univille.motortycoon.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Strings;

import br.edu.univille.motortycoon.entity.Usuario;
import br.edu.univille.motortycoon.repository.UsuarioRepository;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    public Optional<Usuario> obterPeloId(long id){
        return repository.findById(id);
    }

    public Optional<Usuario> obterPeloEmail(String email){
        return repository.findByEmail(email);
    }

    public List<Usuario> obterTodos(){
        return repository.findAll();
    }
    
    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }

    public void excluir(Usuario usuario) {
        repository.delete(usuario);
    }
}
