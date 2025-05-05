package br.edu.univille.capacete.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.univille.capacete.entity.Equipamento;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
    
}
