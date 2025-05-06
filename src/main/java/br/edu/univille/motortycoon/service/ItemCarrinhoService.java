package br.edu.univille.motortycoon.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Strings;

import br.edu.univille.motortycoon.entity.ItemCarrinho;
import br.edu.univille.motortycoon.repository.ItemCarrinhoRepository;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
public class ItemCarrinhoService {
    @Autowired
    private ItemCarrinhoRepository repository;

    public Optional<ItemCarrinho> obterPeloId(long id){
        return repository.findById(id);
    }

    public List<ItemCarrinho> obterTodos(){
        return repository.findAll();
    }

    public void salvar(ItemCarrinho ItemCarrinho) {
        repository.save(ItemCarrinho);
    }

    public void excluir(ItemCarrinho ItemCarrinho) {
        repository.delete(ItemCarrinho);
    }
}
