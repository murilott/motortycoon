package br.edu.univille.capacete.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Strings;

import br.edu.univille.capacete.entity.ItemCarrinho;
import br.edu.univille.capacete.repository.ItemCarrinhoRepository;
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
        return repository.findAll(Sort.by("marca"));
    }

    public void salvar(ItemCarrinho ItemCarrinho) {
        repository.save(ItemCarrinho);
    }

    public void excluir(ItemCarrinho ItemCarrinho) {
        repository.delete(ItemCarrinho);
    }
}
