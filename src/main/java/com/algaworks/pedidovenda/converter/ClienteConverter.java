package com.algaworks.pedidovenda.converter;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.repository.Clientes;
import com.algaworks.pedidovenda.repository.Usuarios;
import com.algaworks.pedidovenda.util.cdi.CDIServiceLocator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Cliente.class)
public class ClienteConverter implements Converter {

    //@Inject
    private Clientes clientes;

    public ClienteConverter() {
        this.clientes = CDIServiceLocator.getBean(Clientes.class);
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Cliente retorno = null;
        if (s != null) {
            Long id = Long.valueOf(s);
            retorno = clientes.porId(id);
        }
        return retorno;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if(o != null) {
            Cliente cliente = (Cliente) o;
            return cliente.getId() == null ? null : cliente.getId().toString();
        }
        return "";
    }
}
