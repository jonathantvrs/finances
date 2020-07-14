package com.jonathantvrs.minhasfinancas.enums;

public enum TipoLancamento {
    DESPESA("DESPESA"),
    RECEITA("RECEITA");

    public String tipoValue;

    TipoLancamento(String tipo) {
        this.tipoValue = tipo;
    }

    public String getTipoValue() {
        return this.tipoValue;
    }
}
