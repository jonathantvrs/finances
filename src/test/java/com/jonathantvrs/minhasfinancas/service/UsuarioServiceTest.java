package com.jonathantvrs.minhasfinancas.service;

import com.jonathantvrs.minhasfinancas.exceptions.RegraNegocioException;
import com.jonathantvrs.minhasfinancas.models.Usuario;
import com.jonathantvrs.minhasfinancas.repositories.UsuarioRepository;
import com.jonathantvrs.minhasfinancas.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {

    private UsuarioService service;
    @MockBean
    private UsuarioRepository repository;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        this.service = new UsuarioServiceImpl(this.repository);
        this.usuario = Usuario.builder().nome("LeonardoVascon").email("vascon@gmail.com").build();
    }

    @Test
    @DisplayName("Teste que verifica que Email ainda não existe")
    public void validaEmailNaoExistente() {
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);

        Assertions.assertDoesNotThrow(() -> service.validarEmail("email@email.com"));
    }

    @Test
    @DisplayName("Teste que verifica que Email já existe")
    public void validaEmailExistente() {
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

        Assertions.assertThrows(RegraNegocioException.class, () -> service.validarEmail("vascon@gmail.com"));
    }

    @Test
    @DisplayName("Teste que verifica a mensagem de exceção de Email existente")
    public void verificaMensagemDeEmailExistente() {
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

        try {
            service.validarEmail("vascon@gmail.com");
        } catch (RegraNegocioException rne) {
            Assertions.assertEquals("Já existe um usuário cadastrado com este email.", rne.getMessage());
        }
    }

}