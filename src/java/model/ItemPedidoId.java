/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
/**
 *
 * @author SAMUEL
 */
public class ItemPedidoId implements java.io.Serializable{
    private Pedido pedido;
    private TipoRoupa tipoRoupa;

    @ManyToOne
    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @ManyToOne
    public TipoRoupa getTipoRoupa() {
        return tipoRoupa;
    }

    public void setTipoRoupa(TipoRoupa tipoRoupa) {
        this.tipoRoupa = tipoRoupa;
    }
    
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemPedidoId that = (ItemPedidoId) o;

        if (pedido != null ? !pedido.equals(that.pedido) : that.pedido != null) return false;
        if (tipoRoupa != null ? !tipoRoupa.equals(that.tipoRoupa) : that.tipoRoupa != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (pedido != null ? pedido.hashCode() : 0);
        result = 31 * result + (tipoRoupa != null ? tipoRoupa.hashCode() : 0);
        return result;
    }    
    
}
