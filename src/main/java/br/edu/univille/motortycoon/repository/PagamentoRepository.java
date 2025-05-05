package br.edu.univille.capacete.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.univille.capacete.entity.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    
}
