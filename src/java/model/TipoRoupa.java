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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author SAMUEL
 */
@javax.persistence.Entity
@javax.persistence.Table(name="TipoRoupa")
public class TipoRoupa implements Serializable{
    private int id;
    private String descricao;
    private int prazoLavagem;
    private Set<ItemPedido> roupasPedido = new HashSet<ItemPedido>(0);
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getPrazoLavagem() {
        return prazoLavagem;
    }

    public void setPrazoLavagem(int prazoLavagem) {
        this.prazoLavagem = prazoLavagem;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.tipoRoupa")
    public Set<ItemPedido> getRoupasPedido() {
        return roupasPedido;
    }

    public void setRoupasPedido(Set<ItemPedido> roupasPedido) {
        this.roupasPedido = roupasPedido;
    }
    
}
