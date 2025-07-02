package br.edu.univille.motortycoon.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.univille.motortycoon.entity.Categoria;
import br.edu.univille.motortycoon.repository.CategoriaRepository;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository repository;

    public Optional<Categoria> obterPeloId(long id){
        return repository.findById(id);
    }

    public List<Categoria> obterTodos(){
        return repository.findAll();
    }

    public Categoria salvar(Categoria Categoria) {
        return repository.save(Categoria);
    }

    public void excluir(Categoria Categoria) {
        repository.delete(Categoria);
    }
}
