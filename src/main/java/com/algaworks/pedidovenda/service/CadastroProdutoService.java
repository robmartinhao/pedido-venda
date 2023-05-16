package com.algaworks.pedidovenda.service;

import com.algaworks.pedidovenda.model.Produto;
import com.algaworks.pedidovenda.repository.Produtos;

import javax.inject.Inject;
import java.io.Serializable;

public class CadastroProdutoService implements Serializable {

    @Inject
    private Produtos produtos;

    public Produto salvar(Produto produto) {
        Produto produtoExistente = produtos.porSku(produto.getSku());
        if (produtoExistente != null) {
            throw new NegocioException("Já existe um produto com o SKU informado.");
        }
        return produtos.guardar(produto);
    }
}
