package com.jonathantvrs.minhasfinancas.models;

import com.jonathantvrs.minhasfinancas.enums.StatusLancamento;
import com.jonathantvrs.minhasfinancas.enums.TipoLancamento;
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
import java.util.Objects;

/**
 * Classe que representa um lançamento financeiro no sistema
 */
@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public TipoLancamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoLancamento tipo) {
        this.tipo = tipo;
    }

    public StatusLancamento getStatus() {
        return status;
    }

    public void setStatus(StatusLancamento status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lancamento that = (Lancamento) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(descricao, that.descricao) &&
                Objects.equals(ano, that.ano) &&
                Objects.equals(mes, that.mes) &&
                Objects.equals(usuario, that.usuario) &&
                Objects.equals(valor, that.valor) &&
                Objects.equals(dataCadastro, that.dataCadastro) &&
                tipo == that.tipo &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descricao, ano, mes, usuario, valor, dataCadastro, tipo, status);
    }
}
