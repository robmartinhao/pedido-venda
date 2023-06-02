package com.algaworks.pedidovenda.converter;

import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.repository.Pedidos;
import com.algaworks.pedidovenda.util.cdi.CDIServiceLocator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Pedido.class)
public class PedidoConverter implements Converter {

    //@Inject
    private Pedidos pedidos;

    public PedidoConverter() {
        this.pedidos = CDIServiceLocator.getBean(Pedidos.class);
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Pedido retorno = null;
        if (s != null) {
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
