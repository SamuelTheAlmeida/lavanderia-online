/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *
 * @author SAMUEL
 */
@javax.persistence.Entity
@javax.persistence.Table(name="Pedido")
public class Pedido implements Serializable{
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   private int id;
   private List<TipoRoupa> roupasPedido; 
   private int prazo;
   private double valorTotal;
   private Cliente cliente;    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TipoRoupa> getRoupasPedido() {
        return roupasPedido;
    }

    public void setRoupasPedido(List<TipoRoupa> roupasPedido) {
        this.roupasPedido = roupasPedido;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
   
   
}
