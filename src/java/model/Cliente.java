/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import java.io.Serializable;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@javax.persistence.Entity
@javax.persistence.Table(name="Cliente")
public class Cliente implements Serializable{
    private int id;
    private Usuario usuario = new Usuario();
    private String endereco;
    private Cidade cidade = new Cidade();
    private String telefone;
    private String cpf;
    private String sexo;

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO) 
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToOne(fetch = FetchType.EAGER)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @OneToOne(fetch = FetchType.EAGER)
    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    
}


    

