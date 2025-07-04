package br.edu.univille.motortycoon.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Strings;

import br.edu.univille.motortycoon.entity.Carrinho;
import br.edu.univille.motortycoon.repository.CarrinhoRepository;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
public class CarrinhoService {
    @Autowired
    private CarrinhoRepository repository;

    public Optional<Carrinho> obterPeloId(long id){
        return repository.findById(id);
    }

    public List<Carrinho> obterTodos(){
        return repository.findAll();
    }

    public Carrinho salvar(Carrinho Carrinho) {
        return repository.save(Carrinho);
    }

    public void excluir(Carrinho Carrinho) {
        repository.delete(Carrinho);
    }
}
