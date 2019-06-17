/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UsuarioDAO;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.Usuario;
import util.Constantes;
import util.Utils;

@SessionScoped
@ManagedBean(name="loginMB")
public class LoginMB {
    private Usuario usuario = new Usuario();

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public String autenticar() {
        FacesContext context = FacesContext.getCurrentInstance();
        UsuarioDAO dao = new UsuarioDAO();
        String senha = this.usuario.getSenha();
        this.usuario.setSenha(Utils.MD5(senha));
        this.usuario = dao.autenticar(usuario);
        if (this.usuario != null) {
            System.out.println("mb: " + usuario.getIdPerfil());
            if (this.isCliente()) return "pedidosCliente?faces-redirect=true";
            if (this.isFuncionario()) return "pedidosPainel?faces-redirect=true";
        } else {
            this.usuario = new Usuario();
            context.addMessage(null, new FacesMessage("Login ou senha incorretos"));  
        }
        return null;
    }
    
    public void checarPerfilFuncionario() throws IOException {
       if (!this.isFuncionario()) {
           System.out.println("não é funcionario");
           ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
           ec.redirect(ec.getRequestContextPath() + "/pedidosCliente.xhtml");
       }
   }   
    
    public void checarPerfilCliente() throws IOException {
       if (!this.isCliente()) {
           System.out.println("não é cliente");
           ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
           ec.redirect(ec.getRequestContextPath() + "/pedidosPainel.xhtml");
       }
   }      
    
    public boolean isLogado() {
        return usuario.getEmail() != null;
    }
    
    public boolean isCliente() {
        return (this.usuario.getIdPerfil() == Constantes.PERFIL_CLIENTE);
    }
    
    public boolean isFuncionario() {
        return (this.usuario.getIdPerfil() == Constantes.PERFIL_FUNCIONARIO);
    }
    
    public String logout() {
        FacesContext.getCurrentInstance().
        getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }
}
