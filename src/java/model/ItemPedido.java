/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author SAMUEL
 */
@javax.persistence.Entity
@javax.persistence.Table(name="ItemPedido")
@AssociationOverrides({
		@AssociationOverride(name = "Pedido", 
			joinColumns = @JoinColumn(name = "id")),
		@AssociationOverride(name = "TipoRoupa", 
			joinColumns = @JoinColumn(name = "id")) })
public class ItemPedido implements Serializable{
    private ItemPedidoId pk = new ItemPedidoId();
    private Pedido pedido;
    private TipoRoupa tipoRoupa;   
    private int quantidade;
    
    public ItemPedido(){
        this.tipoRoupa = new TipoRoupa();
    }

    @EmbeddedId
    public ItemPedidoId getPk() {
            return pk;
    }

    public void setPk(ItemPedidoId pk) {
            this.pk = pk;
    }

    @Transient
    public Pedido getPedido() {
            return getPk().getPedido();
    }

    public void setPedido(Pedido pedido) {
            getPk().setPedido(pedido);
    }

    @Transient
    public TipoRoupa getTipoRoupa() {
            return getPk().getTipoRoupa();
    }

    public void setTipoRoupa(TipoRoupa tipoRoupa) {
            getPk().setTipoRoupa(tipoRoupa);
    }


    public boolean equals(Object o) {
            if (this == o)
                    return true;
            if (o == null || getClass() != o.getClass())
                    return false;

            ItemPedido that = (ItemPedido) o;

            if (getPk() != null ? !getPk().equals(that.getPk())
                            : that.getPk() != null)
                    return false;

            return true;
    }

    public int hashCode() {
            return (getPk() != null ? getPk().hashCode() : 0);
    }
        
    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
}
