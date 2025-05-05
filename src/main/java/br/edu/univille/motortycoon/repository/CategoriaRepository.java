package br.edu.univille.motortycoon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.univille.motortycoon.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
}
