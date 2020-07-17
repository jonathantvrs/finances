package com.jonathantvrs.minhasfinancas.repositories;

import com.jonathantvrs.minhasfinancas.models.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository repository;

    private Usuario u1;

    @BeforeEach
    void setUp() {
        this.u1 = Usuario.builder().nome("usuario").email("usuario@email.com").build();
        repository.save(u1);
    }

    @Test
    public void verificaQueOEmailExiste() {
        boolean result = repository.existsByEmail("usuario@email.com");

        Assertions.assertTrue(result);
    }

    @Test
    public void verificaQueOEmailNaoExiste() {
        repository.deleteAll();

        boolean result = repository.existsByEmail("usuario@email.com");

        Assertions.assertFalse(result);
    }
}