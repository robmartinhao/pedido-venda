package com.algaworks.pedidovenda.model;

import java.io.Serializable;
import java.util.Objects;

public class Categoria implements Serializable {

    private Long id;

    private Categoria categoriaPai;
    private String descricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Categoria getCategoriaPai() {
        return categoriaPai;
    }

    public void setCategoriaPai(Categoria categoriaPai) {
        this.categoriaPai = categoriaPai;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return Objects.equals(id, categoria.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
