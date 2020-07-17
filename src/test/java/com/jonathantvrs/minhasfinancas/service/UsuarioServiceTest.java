package com.jonathantvrs.minhasfinancas.service;

import com.jonathantvrs.minhasfinancas.exceptions.ErroAutenticacaoException;
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

import java.util.Optional;

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
        this.usuario = Usuario.builder()
                .nome("LeonardoVascon")
                .email("vascon@gmail.com")
                .senha("123456")
                .build();
    }

    @Test
    @DisplayName("Teste de autenticação de usuário com sucesso")
    public void autenticaUsuarioComSucesso() {
        Mockito.when(repository.findByEmail("vascon@gmail.com")).thenReturn(Optional.of(this.usuario));

        Usuario result = service.autenticar("vascon@gmail.com", "123456");

        Assertions.assertNotNull(result);
        Assertions.assertDoesNotThrow(() -> service.autenticar("vascon@gmail.com", "123456"));
    }

    @Test
    @DisplayName("Teste de lançamento de exceção na autenticação caso não exista usuário com Email informado")
    public void excecaoUsuarioComEmailNaoEncontrado() {
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(ErroAutenticacaoException.class, () -> service.autenticar(usuario.getEmail(), usuario.getSenha()));
    }

    @Test
    @DisplayName("Teste de mensagem de lançamento de exceção na autenticação caso não exista usuário com Email informado")
    public void verificaMensagemUsuarioComEmailNaoEncontrado() {
        try {
            service.autenticar(usuario.getEmail(), usuario.getSenha());
        } catch (ErroAutenticacaoException eae) {
            Assertions.assertEquals("Não existe usuário com o email informado.", eae.getMessage());
        }
    }

    @Test
    @DisplayName("Teste de lançamento de exceção na autenticação caso senha não dê Match")
    public void excecaoSenhaInvalida() {
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

        Assertions.assertThrows(ErroAutenticacaoException.class, () -> service.autenticar(usuario.getEmail(), "123"));
    }

    @Test
    @DisplayName("Teste de mensagem de lançamento de exceção na autenticação caso a senha informada seja inválida")
    public void verificaMensagemUsuarioComSenhaInvalida() {
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

        try {
            service.autenticar(usuario.getEmail(), "123");
        } catch (ErroAutenticacaoException eae) {
            Assertions.assertEquals("Senha inválida", eae.getMessage());
        }
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