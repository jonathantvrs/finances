package com.jonathantvrs.minhasfinancas.controllers;

import com.jonathantvrs.minhasfinancas.dtos.LancamentoDTO;
import com.jonathantvrs.minhasfinancas.enums.StatusLancamento;
import com.jonathantvrs.minhasfinancas.enums.TipoLancamento;
import com.jonathantvrs.minhasfinancas.exceptions.RegraNegocioException;
import com.jonathantvrs.minhasfinancas.models.Lancamento;
import com.jonathantvrs.minhasfinancas.models.Usuario;
import com.jonathantvrs.minhasfinancas.service.LancamentoService;
import com.jonathantvrs.minhasfinancas.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoController {

    private LancamentoService service;
    private UsuarioService usuarioService;

    public LancamentoController(LancamentoService service, UsuarioService usuarioService) {
        this.service = service;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity buscar(
        @RequestParam(value = "descricao", required = false) String descricao,
        @RequestParam(value = "mes", required = false) Integer mes,
        @RequestParam(value = "ano", required = false) Integer ano,
        @RequestParam("usuario") Long idUsuario
    ) {
        Lancamento lancamentoFiltro = new Lancamento();
        lancamentoFiltro.setDescricao(descricao);
        lancamentoFiltro.setMes(mes);
        lancamentoFiltro.setAno(ano);

        Optional<Usuario> usuario = usuarioService.obterPorId(idUsuario);
        if (usuario.isPresent()) {
            lancamentoFiltro.setUsuario(usuario.get());
        } else {
            return ResponseEntity.badRequest().body("Usuário não encontrado para o id informado");
        }

        List<Lancamento> lancamentos = service.buscar(lancamentoFiltro);
        return ResponseEntity.ok(lancamentos);
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody LancamentoDTO dto) {
        try {
            Lancamento lancamento = converter(dto);
            lancamento = service.salvar(lancamento);
            return new ResponseEntity(lancamento, HttpStatus.CREATED);
        } catch (RegraNegocioException rne) {
            return ResponseEntity.badRequest().body(rne.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LancamentoDTO dto) {
        return service.obterPorId(id).map(lancamento -> {
            try {
                Lancamento l = converter(dto);
                l.setId(lancamento.getId());
                service.atualizar(l);
                return ResponseEntity.ok(l);
            } catch (RegraNegocioException rne) {
                return ResponseEntity.badRequest().body(rne.getMessage());
            }
        }).orElseGet(() -> new ResponseEntity("Lancamento não encontrado", HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id) {
        return service.obterPorId(id).map(lancamento -> {
            service.deletar(lancamento);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseGet(() -> new ResponseEntity("Lancamento não encontrado", HttpStatus.BAD_REQUEST));
    }

    private Lancamento converter(LancamentoDTO dto) {
        Lancamento lancamento = new Lancamento();
        lancamento.setId(dto.getId());
        lancamento.setDescricao(dto.getDescricao());
        lancamento.setAno(dto.getAno());
        lancamento.setMes(dto.getMes());
        lancamento.setValor(dto.getValor());

        Usuario usuario = usuarioService
                .obterPorId(dto.getUsuario())
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado"));
        lancamento.setUsuario(usuario);

        if (dto.getTipo() != null)
            lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
        if (dto.getStatus() != null)
            lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));

        return lancamento;
    }
}
