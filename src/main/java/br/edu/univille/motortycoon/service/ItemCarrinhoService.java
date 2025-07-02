package br.edu.univille.motortycoon.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.univille.motortycoon.entity.ItemCarrinho;
import br.edu.univille.motortycoon.repository.ItemCarrinhoRepository;

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

    public ItemCarrinho salvar(ItemCarrinho ItemCarrinho) {
        return repository.save(ItemCarrinho);
    }

    public void excluir(ItemCarrinho ItemCarrinho) {
        repository.delete(ItemCarrinho);
    }
}
