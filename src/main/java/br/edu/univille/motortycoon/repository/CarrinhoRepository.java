package br.edu.univille.motortycoon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.univille.motortycoon.entity.Carrinho;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    
}
