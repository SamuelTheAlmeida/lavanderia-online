/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/**
 *
 * @author SAMUEL
 */
@javax.persistence.Entity
@javax.persistence.Table(name="Pedido")
public class Pedido implements Serializable{
   private int id;
   private Set<ItemPedido> roupasPedido = new HashSet<ItemPedido>(0);
   private int prazo;
   private double valorTotal;
  // private Cliente cliente;    
   
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

   // public Cliente getCliente() {
   //     return cliente;
   // }

   // public void setCliente(Cliente cliente) {
   //     this.cliente = cliente;
   // }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.pedido", cascade=CascadeType.ALL)
    public Set<ItemPedido> getRoupasPedido() {
        return roupasPedido;
    }
    

    public void setRoupasPedido(Set<ItemPedido> roupasPedido) {
        this.roupasPedido = roupasPedido;
    }
    
    
    

}
