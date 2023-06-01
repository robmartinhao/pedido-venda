package com.algaworks.pedidovenda.service;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.repository.Clientes;
import com.algaworks.pedidovenda.util.jpa.Transactional;

import javax.inject.Inject;
import java.io.Serializable;

public class CadastroClienteService implements Serializable {

    @Inject
    private Clientes clientes;

    @Transactional
    public Cliente salvar(Cliente cliente) {
        Cliente clienteExistente = clientes.porEmail(cliente.getEmail());
        if (clienteExistente != null && !clienteExistente.equals(cliente)) {
            throw new NegocioException("Já existe um Cliente com o E-mail informado.");
        }
        return clientes.guardar(cliente);
    }
}
