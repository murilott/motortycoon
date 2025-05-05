package br.edu.univille.motortycoon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.univille.motortycoon.entity.Equipamento;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
    
}
