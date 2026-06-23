package com.grupoum.projeto_fera.model;

import java.io.Serializable;
import java.util.Objects;

public class OrcamentoProdutoId implements Serializable {
    private Long orcamento;
    private Long produto;

    // Getters, setters, equals, and hashCode
    public Long getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Long orcamento) {
        this.orcamento = orcamento;
    }

    public Long getProduto() {
        return produto;
    }

    public void setProduto(Long produto) {
        this.produto = produto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrcamentoProdutoId that = (OrcamentoProdutoId) o;
        return Objects.equals(orcamento, that.orcamento) &&
               Objects.equals(produto, that.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orcamento, produto);
    }
}
