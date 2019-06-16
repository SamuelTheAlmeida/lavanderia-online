/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@javax.persistence.Entity
@javax.persistence.Table(name="Cidade")
public class Estado implements Serializable{
    private int id;
    private String sigla;

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO) 
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
    
    
}
