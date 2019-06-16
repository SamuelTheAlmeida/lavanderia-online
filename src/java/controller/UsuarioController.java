/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UsuarioDAO;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Cliente;
import model.Usuario;
import java.security.*;
import model.Cliente;

@ManagedBean(name="UsuarioController")
@SessionScoped
public class UsuarioController {
    
    private model.Cliente cliente;
    
    public UsuarioController() {
        cliente = new Cliente();
    }

    public void salvar() {
        System.out.println("cpf: " + cliente.getCpf());
        System.out.println("endereco: " + cliente.getEndereco());
        System.out.println("cidade: " + cliente.getCidade().getNome());
        System.out.println("estado: " + cliente.getCidade().getEstado().getSigla());
        System.out.println("telefone: " + cliente.getTelefone());
        System.out.println("id usuario: " + cliente.getUsuario().getId());
        System.out.println("email: " + cliente.getUsuario().getEmail());
        System.out.println("senha: " + cliente.getUsuario().getSenha());
        System.out.println("nome: " + cliente.getUsuario().getNome());
        System.out.println("idperfil: " + cliente.getUsuario().getIdPerfil());
        
        String senha = this.cliente.getUsuario().getSenha();
        this.cliente.getUsuario().setSenha(MD5(senha));
        this.cliente.getUsuario().setIdPerfil(1);

        System.out.println("email: " + cliente.getUsuario().getEmail());
        System.out.println("senha: " + cliente.getUsuario().getSenha());
        System.out.println("nome: " + cliente.getUsuario().getNome());
        System.out.println("idperfil: " + cliente.getUsuario().getIdPerfil());
        new UsuarioDAO().cadastrar(cliente.getUsuario());
        
    }

    public model.Cliente getCliente() {
        return cliente;
    }

    public void setCliente(model.Cliente cliente) {
        this.cliente = cliente;
    }
    
    public String MD5(String md5) {
        try {
             java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
             byte[] array = md.digest(md5.getBytes());
             StringBuffer sb = new StringBuffer();
             for (int i = 0; i < array.length; ++i) {
               sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
             return sb.toString();
         } catch (java.security.NoSuchAlgorithmException e) {
         }
         return null;
    }
    
}
