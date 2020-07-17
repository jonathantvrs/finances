package com.jonathantvrs.minhasfinancas.service;

import com.jonathantvrs.minhasfinancas.exceptions.RegraNegocioException;
import com.jonathantvrs.minhasfinancas.models.Usuario;
import com.jonathantvrs.minhasfinancas.repositories.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService service;

    @Autowired
    private UsuarioRepository repository;

    @Test
    @DisplayName("Teste que verifica que Email ainda não existe")
    public void validaEmailNaoExistente() {
        repository.deleteAll();

        service.validarEmail("email@email.com");
    }

    @Test
    @DisplayName("Teste que verifica que Email já existe")
    public void validaEmailExistente() {
        repository.deleteAll();

        Usuario u = Usuario.builder().nome("LeonardoVascon").email("vascon@gmail.com").build();
        repository.save(u);

        Assertions.assertThrows(RegraNegocioException.class, () -> service.validarEmail("vascon@gmail.com"));
    }

    @Test
    @DisplayName("Teste que verifica a mensagem de erro de Email existente")
    public void verificaMensagemDeEmailExistente() {
        repository.deleteAll();

        Usuario u = Usuario.builder().nome("LeonardoVascon").email("vascon@gmail.com").build();
        repository.save(u);

        try {
            service.validarEmail("vascon@gmail.com");
        } catch (RegraNegocioException rne) {
            Assertions.assertEquals("Já existe um usuário cadastrado com este email.", rne.getMessage());
        }
    }

}