package com.algaworks.pedidovenda.converter;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.repository.Clientes;
import org.apache.commons.lang3.StringUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(forClass = Cliente.class)
public class ClienteConverter implements Converter {

    @Inject
    private Clientes clientes;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Cliente retorno = null;
        if (StringUtils.isNotEmpty(s)) {
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
