package com.algaworks.pedidovenda.controller;

import com.algaworks.pedidovenda.model.Categoria;
import com.algaworks.pedidovenda.model.Produto;
import com.algaworks.pedidovenda.repository.Categorias;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable {

    @Inject
    private Categorias categorias;
    private Produto produto;
    @NotNull
    private Categoria categoriaPai;
    private List<Categoria> categoriasRaizes;
    private List<Categoria> subcategorias;

    public void inicializar() {
        System.out.println("Inicializando...");
        if(FacesUtil.isNotPostback()) {
            categoriasRaizes = categorias.raizes();
        }
    }

    public void carregarSubcategorias(){
        this.subcategorias = categorias.subcategoriasDe(categoriaPai);
    }

    public CadastroProdutoBean() {
        this.produto = new Produto();
    }

    public void salvar() {
        System.out.println("Categoria pai selecionada: " + categoriaPai.getDescricao());
        System.out.println("Subcategoria selecionada: " + produto.getCategoria().getDescricao());
    }

    public Produto getProduto() {
        return produto;
    }

    public Categoria getCategoriaPai() {
        return categoriaPai;
    }

    public void setCategoriaPai(Categoria categoriaPai) {
        this.categoriaPai = categoriaPai;
    }

    public List<Categoria> getCategoriasRaizes() {
        return categoriasRaizes;
    }

    public List<Categoria> getSubcategorias() {
        return subcategorias;
    }

    public void setSubcategorias(List<Categoria> subcategorias) {
        this.subcategorias = subcategorias;
    }
}
