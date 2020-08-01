package com.jonathantvrs.minhasfinancas.controllers;

import com.jonathantvrs.minhasfinancas.dtos.LancamentoDTO;
import com.jonathantvrs.minhasfinancas.service.LancamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoController {

    private LancamentoService service;

    public LancamentoController(LancamentoService service) {
        this.service = service;
    }

    public ResponseEntity salvar(@RequestBody LancamentoDTO dto) {

    }
}
