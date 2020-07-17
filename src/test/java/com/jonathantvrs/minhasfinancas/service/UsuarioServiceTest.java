package com.jonathantvrs.minhasfinancas.service;

import com.jonathantvrs.minhasfinancas.exceptions.RegraNegocioException;
import com.jonathantvrs.minhasfinancas.models.Usuario;
import com.jonathantvrs.minhasfinancas.repositories.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
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
    public void validaEmailNaoExistente() {
        repository.deleteAll();

        service.validarEmail("email@email.com");
    }

    @Test
    public void validaEmailExistente() {
        repository.deleteAll();

        Usuario u = Usuario.builder().nome("LeonardoVascon").email("chifradoporshampoo@gmail.com").build();
        repository.save(u);

        Assertions.assertThrows(RegraNegocioException.class, () -> service.validarEmail("chifradoporshampoo@gmail.com"));
    }

}