package br.edu.univille.capacete.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.univille.capacete.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
}
