package br.edu.univille.capacete.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.univille.capacete.entity.ItemCarrinho;

public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {
    
}
