package com.jonathantvrs.minhasfinancas.enums;

public enum StatusLancamento {
    PENDENTE("PENDENTE"),
    CANCELADO("CANCELADO"),
    EFETIVADO("EFETIVADO");

    public String statusValue;

    StatusLancamento(String status) {
        this.statusValue = status;
    }

    public String getStatusValue() {
        return this.statusValue;
    }
}
