package com.algaworks.pedidovenda.converter;

import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.repository.Pedidos;
import com.algaworks.pedidovenda.util.cdi.CDIServiceLocator;
import org.apache.commons.lang3.StringUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(forClass = Pedido.class)
public class PedidoConverter implements Converter {

    @Inject
    private Pedidos pedidos;


    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Pedido retorno = null;
        if (StringUtils.isNotEmpty(s)) {
            Long id = Long.valueOf(s);
            retorno = pedidos.porId(id);
        }
        return retorno;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if(o != null) {
            Pedido pedido = (Pedido) o;
            return pedido.getId() == null ? null : pedido.getId().toString();
        }
        return "";
    }
}
