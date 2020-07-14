package com.jonathantvrs.minhasfinancas.models;

import com.jonathantvrs.minhasfinancas.enums.StatusLancamento;
import com.jonathantvrs.minhasfinancas.enums.TipoLancamento;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Classe que representa um lançamento financeiro no sistema
 */
@Entity
@Data
@Table(name = "lancamento", schema = "financas")
public class Lancamento {
    /**
     * Identifica unicamente um lançamento financeiro no sistema
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Descrição do lançamento financeiro
     */
    @Column(name = "descricao")
    private String descricao;

    /**
     * Ano do lançamento financeiro
     */
    @Column(name = "ano")
    private Integer ano;

    /**
     * Mês do lançamento financeiro
     */
    @Column(name = "mes")
    private Integer mes;

    /**
     * Usuário ao qual pertence o lançamento financeiro
     */
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    /**
     * Valor do lançamento financeiro
     */
    @Column(name = "valor")
    private BigDecimal valor;

    /**
     * Data de cadastro do lançamento financeiro
     */
    @Column(name = "data_cadastro")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dataCadastro;

    /**
     * Tipo do lançamento financeiro
     * Ele pode ser: (Receita, Despesa)
     */
    @Column(name = "tipo")
    @Enumerated(value = EnumType.STRING)
    private TipoLancamento tipo;

    /**
     * Status do lançamento financeiro
     * Ele pode ser: (Pendente, Cancelado, Efetivado)
     */
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private StatusLancamento status;
}
