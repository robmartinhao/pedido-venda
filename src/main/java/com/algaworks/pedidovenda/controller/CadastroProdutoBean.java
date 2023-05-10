package com.algaworks.pedidovenda.controller;

import com.algaworks.pedidovenda.model.Produto;

import javax.faces.bean.ViewScoped;
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
        System.out.println("Teste");
    }

    public Produto getProduto() {
        return produto;
    }
}
