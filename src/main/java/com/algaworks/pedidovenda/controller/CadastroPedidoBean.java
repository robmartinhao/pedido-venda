package com.algaworks.pedidovenda.controller;

import com.algaworks.pedidovenda.model.*;
import com.algaworks.pedidovenda.repository.Clientes;
import com.algaworks.pedidovenda.repository.Produtos;
import com.algaworks.pedidovenda.repository.Usuarios;
import com.algaworks.pedidovenda.service.CadastroPedidoService;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.validation.SKU;
import org.apache.commons.lang3.StringUtils;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable {

    @Inject
    private CadastroPedidoService cadastroPedidoService;
    @Inject
    private Clientes clientes;
    @Inject
    private Usuarios usuarios;
    @Inject
    private Produtos produtos;
    @SKU
    private String sku;
    private Pedido pedido;
    private List<Usuario> vendedores;
    private Produto produtoLinhaEditavel;

    public CadastroPedidoBean() {
        limpar();
    }

    public void limpar() {
        this.pedido = new Pedido();
        pedido.setEnderecoEntrega(new EnderecoEntrega());
    }

    public void inicializar() {
        if (FacesUtil.isNotPostback()) {
            this.vendedores = this.usuarios.vendedores();
            this.pedido.adicionaItemVazio();
            this.recalcularPedido();
        }
    }

    public void salvar() {
        this.pedido = cadastroPedidoService.salvar(this.pedido);
        this.pedido.adicionaItemVazio();
        FacesUtil.addInfoMessage("Pedido salvo com sucesso!");
    }

    public List<Produto> completarProduto(String nome) {
        return this.produtos.porNome(nome);
    }

    public void carregarProdutoPorSku() {
        if (StringUtils.isNotEmpty(this.sku)) {
            this.produtoLinhaEditavel = this.produtos.porSku(this.sku);
            this.carregarProdutoLinhaEditavel();
        }
    }

    public void carregarProdutoLinhaEditavel() {
        ItemPedido item = this.pedido.getItens().get(0);

        if (this.produtoLinhaEditavel != null) {
            if (this.existeItemComProduto(this.produtoLinhaEditavel)) {
                FacesUtil.addErrorMessage("Já existe um item no pedido com o produto informado.");
            } else {
                item.setProduto(this.produtoLinhaEditavel);
                item.setValorUnitario(this.produtoLinhaEditavel.getValorUnitario());

                this.pedido.adicionaItemVazio();
                this.produtoLinhaEditavel = null;
                this.sku = null;

                this.pedido.recalcularValorTotal();
            }
        }
    }

    private boolean existeItemComProduto(Produto produto) {
         boolean existeItem = false;

         for (ItemPedido item : this.getPedido().getItens()) {
             if (produto.equals(item.getProduto())) {
                 existeItem = true;
                 break;
             }
         }
         return existeItem;
    }

    public FormaPagamento[] getFormasPagamento() {
        return FormaPagamento.values();
    }

    public List<Cliente> completarCliente(String nome) {
        return clientes.porNome(nome);
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<Usuario> getVendedores() {
        return vendedores;
    }

    public boolean isEditando() {
        return this.pedido.getId() != null;
    }

    public void recalcularPedido() {
        if (this.pedido != null) {
            this.pedido.recalcularValorTotal();
        }
    }

    public Produto getProdutoLinhaEditavel() {
        return produtoLinhaEditavel;
    }

    public void setProdutoLinhaEditavel(Produto produtoLinhaEditavel) {
        this.produtoLinhaEditavel = produtoLinhaEditavel;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
