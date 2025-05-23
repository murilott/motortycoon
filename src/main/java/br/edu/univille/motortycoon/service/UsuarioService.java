package br.edu.univille.motortycoon.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    public List<Usuario> obterTodos(){
        return repository.findAll();
    }

    public void salvar(Usuario Usuario) {
        repository.save(Usuario);
    }

    public void excluir(Usuario Usuario) {
        repository.delete(Usuario);
    }
}
