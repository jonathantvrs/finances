package com.jonathantvrs.minhasfinancas.repositories;

import com.jonathantvrs.minhasfinancas.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);
}
