package com.algaworks.pedidovenda.controller;

import com.algaworks.pedidovenda.model.Categoria;
import com.algaworks.pedidovenda.model.Produto;
import com.algaworks.pedidovenda.repository.Categorias;
import com.algaworks.pedidovenda.service.CadastroProdutoService;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable {

    @Inject
    private Categorias categorias;
    @Inject
    private CadastroProdutoService cadastroProdutoService;
    private Produto produto;
    @NotNull
    private Categoria categoriaPai;
    private List<Categoria> categoriasRaizes;
    private List<Categoria> subcategorias;

    public void inicializar() {
        System.out.println("Inicializando...");
        if(FacesUtil.isNotPostback()) {
            categoriasRaizes = categorias.raizes();
            if (this.categoriaPai != null) {
                carregarSubcategorias();
            }
        }
    }

    public void carregarSubcategorias(){
        this.subcategorias = categorias.subcategoriasDe(categoriaPai);
    }

    public CadastroProdutoBean() {
        limpar();
    }

    private void limpar() {
        produto = new Produto();
        categoriaPai = null;
        subcategorias = null;
    }

    public void salvar() {
        this.produto = cadastroProdutoService.salvar(this.produto);
        limpar();
        FacesUtil.addInfoMessage("Produto salvo com sucesso!");
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
        if (this.produto != null) {
            this.categoriaPai = this.produto.getCategoria().getCategoriaPai();
        }
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

    public boolean isEditando() {
        return this.produto.getId() != null;
    }
}
