package com.algaworks.pedidovenda.controller;

import com.algaworks.pedidovenda.model.Categoria;
import com.algaworks.pedidovenda.model.Produto;
import com.algaworks.pedidovenda.repository.Categorias;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable {

    @Inject
    private Categorias categorias;
    private Produto produto;
    private List<Categoria> categoriasRaizes;

    public void inicializar() {
        System.out.println("Inicializando...");
        categoriasRaizes = categorias.raizes();
    }

    public CadastroProdutoBean() {
        this.produto = new Produto();
    }

    public void salvar() {
        System.out.println("Teste");
    }

    public Produto getProduto() {
        return produto;
    }

    public List<Categoria> getCategoriasRaizes() {
        return categoriasRaizes;
    }
}
