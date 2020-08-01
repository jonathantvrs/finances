package com.jonathantvrs.minhasfinancas.controllers;

import com.jonathantvrs.minhasfinancas.dtos.UsuarioDTO;
import com.jonathantvrs.minhasfinancas.exceptions.ErroAutenticacaoException;
import com.jonathantvrs.minhasfinancas.exceptions.RegraNegocioException;
import com.jonathantvrs.minhasfinancas.models.Usuario;
import com.jonathantvrs.minhasfinancas.service.LancamentoService;
import com.jonathantvrs.minhasfinancas.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final LancamentoService lancamentoService;

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

    @GetMapping("/{id}/saldo")
    public ResponseEntity obterSaldo(@PathVariable("id") Long id) {
        Optional<Usuario> usuario = service.obterPorId(id);

        if (!usuario.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        BigDecimal saldo = lancamentoService.obterSaldoPorUsuario(id);
        return ResponseEntity.ok(saldo);
    }
}
