package com.jonathantvrs.minhasfinancas.repositories;

import com.jonathantvrs.minhasfinancas.enums.TipoLancamento;
import com.jonathantvrs.minhasfinancas.models.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
    @Query(value = "SELECT sum(l.valor) " +
            "FROM Lancamento l " +
            "JOIN l.usuario u " +
            "WHERE u.id = ?1 AND l.tipo = ?2 " +
            "GROUP BY u", nativeQuery = true)
    BigDecimal obterSaldoPorTipoLancamentoEUsuario(Long idUsuario, TipoLancamento tipo);
}
