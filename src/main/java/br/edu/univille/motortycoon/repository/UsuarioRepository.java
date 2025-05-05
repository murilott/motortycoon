package br.edu.univille.capacete.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.univille.capacete.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
}
