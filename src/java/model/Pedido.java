/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Cascade;
import util.Constantes;

@javax.persistence.Entity
@javax.persistence.Table(name="Pedido")
public class Pedido implements Serializable{
   private int id;
   private Set<ItemPedido> roupasPedido = new HashSet<ItemPedido>(0);
   private int prazo;
   private double valorTotal;
   private int idStatus;
   private Date data_pedido;
   private Cliente cliente = new Cliente();    
   
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrazo() {
        return prazo;
    }

    public void setPrazo(int prazo) {
        this.prazo = prazo;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    @OneToOne(fetch = FetchType.EAGER)
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pk.pedido", cascade=CascadeType.ALL)
    public Set<ItemPedido> getRoupasPedido() {
        return roupasPedido;
    }
    
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public void setRoupasPedido(Set<ItemPedido> roupasPedido) {
        this.roupasPedido = roupasPedido;
    }

    @Column(insertable=false)
    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    @Column(insertable=false)
    public Date getData_pedido() {
        return data_pedido;
    }

    public void setData_pedido(Date data_pedido) {
        this.data_pedido = data_pedido;
    }
    
    @Transient
    public String getDataFormatada() {
        return new SimpleDateFormat("dd/MM/yyyy").format(this.data_pedido);
    }
    
    @Transient
    public String getStatus() {
        return Constantes.STATUS_PEDIDO.get(this.idStatus);
    }

}
