package com.jonathantvrs.minhasfinancas.controllers;

import com.jonathantvrs.minhasfinancas.dtos.UsuarioDTO;
import com.jonathantvrs.minhasfinancas.exceptions.ErroAutenticacaoException;
import com.jonathantvrs.minhasfinancas.exceptions.RegraNegocioException;
import com.jonathantvrs.minhasfinancas.models.Usuario;
import com.jonathantvrs.minhasfinancas.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/autenticar")
    public ResponseEntity autenticar(@RequestBody UsuarioDTO dto) {
        try {
            Usuario usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getSenha());
            return ResponseEntity.ok(usuarioAutenticado);
        } catch (ErroAutenticacaoException eae) {
            return ResponseEntity.badRequest().body(eae.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody UsuarioDTO dto) {
        Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha()).build();

        try {
            Usuario novoUsuario = service.salvarUsuario(usuario);
            return new ResponseEntity(novoUsuario, HttpStatus.CREATED);
        } catch (RegraNegocioException rne) {
            return ResponseEntity.badRequest().body(rne.getMessage());
        }
    }
}
