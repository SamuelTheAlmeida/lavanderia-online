/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UsuarioDAO;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Usuario;
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
            return "pedidos?faces-redirect=true";
        } else {
            this.usuario = new Usuario();
            context.addMessage(null, new FacesMessage("Login ou senha incorretos"));  
            return null;
        }
    }
    
    public boolean isLogado() {
        return usuario.getEmail() != null;
    }
    
    public String logout() {
        FacesContext.getCurrentInstance().
        getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }
}
