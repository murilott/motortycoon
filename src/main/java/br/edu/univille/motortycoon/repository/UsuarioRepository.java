package br.edu.univille.motortycoon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.edu.univille.motortycoon.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // UserDetails findByEmail(String email);
    Optional<Usuario> findByEmail(String email);
}
