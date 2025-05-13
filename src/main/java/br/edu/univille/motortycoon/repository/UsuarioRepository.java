package br.edu.univille.motortycoon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.univille.motortycoon.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByEmail(String email);
}
