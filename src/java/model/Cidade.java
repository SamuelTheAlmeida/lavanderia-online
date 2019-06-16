/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author SAMUEL
 */
@javax.persistence.Entity
@javax.persistence.Table(name="Cidade")
public class Cidade implements java.io.Serializable{
    private int id;
    private String nome;
    private Estado estado = new Estado();
    
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO) 
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    @OneToOne(fetch = FetchType.EAGER)
    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    
}
