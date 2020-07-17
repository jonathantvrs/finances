package com.jonathantvrs.minhasfinancas.repositories;

import com.jonathantvrs.minhasfinancas.models.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
