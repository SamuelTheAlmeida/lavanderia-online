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


/**
 *
 * @author SAMUEL
 */
@javax.persistence.Entity
@javax.persistence.Table(name="Usuario")
public class Usuario implements Serializable{
   private int id;
   private String email;
   private String senha;
   private String nome;
   private int idPerfil;

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)   
   public int getId() {
       return id;
    }

   public void setId(int id) {
       this.id = id;
   }

   public String getEmail() {
       return email;
   }

   public void setEmail(String email) {
       this.email = email;
   }

   public String getSenha() {
       return senha;
   }

    public void setSenha(String senha) {
        this.senha = senha;
    }

   public String getNome() {
       return nome;
   }

   public void setNome(String nome) {
       this.nome = nome;
   }

   public int getIdPerfil() {
       return idPerfil;
   }

   public void setIdPerfil(int idPerfil) {
       this.idPerfil = idPerfil;
   }
}
