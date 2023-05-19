package com.algaworks.pedidovenda.controller;

import com.algaworks.pedidovenda.model.Produto;
import com.algaworks.pedidovenda.repository.Produtos;
import com.algaworks.pedidovenda.repository.filter.ProdutoFilter;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class PesquisaProdutosBean implements Serializable {

    @Inject
    private Produtos produtos;

    private ProdutoFilter filtro;
    private List<Produto> produtosFiltrados;

    public PesquisaProdutosBean() {
        this.filtro = new ProdutoFilter();
    }

    public void pesquisar() {
        produtosFiltrados = produtos.filtrados(filtro);
    }

    public ProdutoFilter getFiltro() {
        return filtro;
    }

    public List<Produto> getProdutosFiltrados() {
        return produtosFiltrados;
    }
}
