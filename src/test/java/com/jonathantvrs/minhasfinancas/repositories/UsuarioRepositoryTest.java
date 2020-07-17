package com.jonathantvrs.minhasfinancas.repositories;

import com.jonathantvrs.minhasfinancas.models.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        this.usuario = Usuario.builder()
                .nome("usuario")
                .senha("senha")
                .email("usuario@email.com")
                .build();
    }

    @Test
    @DisplayName("Teste de verificação de existência de Usuário com Email")
    public void verificaQueOEmailExiste() {
        entityManager.persist(usuario);

        boolean result = repository.existsByEmail("usuario@email.com");

        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("Teste de verificação de não existência de Usuário com Email")
    public void verificaQueOEmailNaoExiste() {
        boolean result = repository.existsByEmail("usuario@email.com");

        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Teste que busca Usuário por Email com Sucesso")
    public void buscaUsuarioPorEmailComSucesso() {
        entityManager.persist(usuario);

        Optional<Usuario> result = repository.findByEmail("usuario@email.com");

        Assertions.assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Teste que não encontra Usuário com Email")
    public void buscaUsuarioPorEmailInexistente() {
        Optional<Usuario> result = repository.findByEmail("usuario2@email.com");

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Teste de persistência de Usuário com UsuárioRepository")
    public void persisteUsuarioComSucesso() {
        Usuario u = repository.save(usuario);

        Assertions.assertNotNull(u.getId());
    }
}