package com.jonathantvrs.minhasfinancas.service;

import com.jonathantvrs.minhasfinancas.enums.StatusLancamento;
import com.jonathantvrs.minhasfinancas.models.Lancamento;

import java.util.List;

public interface LancamentoService {
    Lancamento salvar(Lancamento lancamento);
    Lancamento atualizar(Lancamento lancamento);
    void deletar(Lancamento lancamento);
    List<Lancamento> buscar(Lancamento lancamentoFiltro);
    void atualizarStatus(Lancamento lancamento, StatusLancamento status);
    void validar(Lancamento lancamento);
}
