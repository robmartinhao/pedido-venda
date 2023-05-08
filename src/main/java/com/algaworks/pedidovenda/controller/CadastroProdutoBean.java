package com.algaworks.pedidovenda.controller;

import com.algaworks.pedidovenda.model.Produto;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable {

    private Produto produto;

    public CadastroProdutoBean() {
        this.produto = new Produto();
    }

    public void salvar() {
        System.out.println(produto.getQuantidadeEstoque());
    }

    public Produto getProduto() {
        return produto;
    }
}
