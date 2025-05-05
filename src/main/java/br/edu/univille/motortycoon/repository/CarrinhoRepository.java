package br.edu.univille.capacete.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.univille.capacete.entity.Carrinho;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    
}
