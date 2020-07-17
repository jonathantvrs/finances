package com.jonathantvrs.minhasfinancas.service;

import com.jonathantvrs.minhasfinancas.models.Usuario;

public interface UsuarioService {
    Usuario autenticar(String email, String senha);
    Usuario salvarUsuario(Usuario usuario);
    void validarEmail(String email);
}
