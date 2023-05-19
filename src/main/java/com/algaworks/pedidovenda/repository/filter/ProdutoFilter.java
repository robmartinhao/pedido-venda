package com.algaworks.pedidovenda.repository.filter;

import com.algaworks.pedidovenda.validation.SKU;

import java.io.Serializable;

public class ProdutoFilter implements Serializable {

    @SKU
    private String sku;
    private String nome;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku == null ? null : sku.toUpperCase();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
