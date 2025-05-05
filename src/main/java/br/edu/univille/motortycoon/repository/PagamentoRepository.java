package br.edu.univille.motortycoon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.univille.motortycoon.entity.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    
}
