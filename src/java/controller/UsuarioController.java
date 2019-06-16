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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Cliente;
import util.Utils;

@ManagedBean(name="UsuarioController")
@SessionScoped
public class UsuarioController {
    
    private model.Cliente cliente;
    
    public UsuarioController() {
        cliente = new Cliente();
    }

    public String salvar() {
        UsuarioDAO dao = new UsuarioDAO();
        String cpf = this.cliente.getCpf().replaceAll("[^a-zA-Z0-9]+","");
        this.cliente.setCpf(cpf);

        if (dao.cpfExiste(cliente.getCpf())) {
            FacesContext.getCurrentInstance().addMessage("cadastroCliente:erroCadastro", new FacesMessage("CPF já cadastrado"));
            return null;
        }
        
        if (dao.emailExiste(cliente.getUsuario().getEmail())) {
            FacesContext.getCurrentInstance().addMessage("cadastroCliente:erroCadastro", new FacesMessage("Email já cadastrado"));
            return null;
        }
        
        String senha = this.cliente.getUsuario().getSenha();
        this.cliente.getUsuario().setSenha(Utils.MD5(senha));
        this.cliente.getUsuario().setIdPerfil(1);
        String telefone = this.cliente.getTelefone().replaceAll("[^a-zA-Z]+","");
        this.cliente.setTelefone(telefone);

        dao.cadastrarUsuario(cliente.getUsuario());
        dao.cadastrarCliente(cliente);
        return "pedidos";
        
    }
    
    public model.Cliente getCliente() {
        return cliente;
    }

    public void setCliente(model.Cliente cliente) {
        this.cliente = cliente;
    }

}
