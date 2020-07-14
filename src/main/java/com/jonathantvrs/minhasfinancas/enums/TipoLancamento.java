package com.jonathantvrs.minhasfinancas.enums;

public enum TipoLancamento {
    DESPESA("Despesa"),
    RECEITA("Receita");

    public String tipoValue;

    TipoLancamento(String tipo) {
        this.tipoValue = tipo;
    }

    public String getTipoValue() {
        return this.tipoValue;
    }
}
