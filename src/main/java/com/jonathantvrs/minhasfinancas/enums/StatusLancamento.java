package com.jonathantvrs.minhasfinancas.enums;

public enum StatusLancamento {
    PENDENTE("Pendente"),
    CANCELADO("Cancelado"),
    EFETIVADO("Efetivado");

    public String statusValue;

    StatusLancamento(String status) {
        this.statusValue = status;
    }

    public String getStatusValue() {
        return this.statusValue;
    }
}
