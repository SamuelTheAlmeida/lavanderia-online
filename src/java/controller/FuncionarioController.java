/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.TipoRoupaDAO;
import dao.UsuarioDAO;
import java.util.Calendar;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import model.Cliente;
import model.Funcionario;
import model.TipoRoupa;
import util.Utils;

/**
 *
 * @author SAMUEL
 */
@ManagedBean(name="FuncionarioController")
@SessionScoped
public class FuncionarioController {
    
    private Funcionario funcionario;
    private TipoRoupa tipoRoupa;
    
    public FuncionarioController() {
        this.funcionario = new Funcionario();
        tipoRoupa = new TipoRoupa();
    }

    public String salvar() {
        UsuarioDAO dao = new UsuarioDAO();
        
        if (dao.emailExiste(funcionario.getUsuario().getEmail())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Email já cadastrado"));
            return null;
        }
        
        String senha = this.funcionario.getUsuario().getSenha();
        this.funcionario.getUsuario().setSenha(Utils.MD5(senha));
        this.funcionario.getUsuario().setIdPerfil(util.Constantes.PERFIL_FUNCIONARIO);
        
        java.util.Date dt = this.funcionario.getDataNascimento();
        java.util.Calendar c = Calendar.getInstance(); 
        c.setTime(dt); 
        c.add(java.util.Calendar.DATE, 1);
        dt = c.getTime();
        this.funcionario.setDataNascimento(dt);
        
        dao.cadastrarUsuario(funcionario.getUsuario());
        dao.cadastrarFuncionario(funcionario);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Funcionário cadastrado com sucesso"));
        return "pedidosPainel?faces-redirect=true";
        
    }
    
    public String salvarTipoRoupa() {
        TipoRoupaDAO dao = new TipoRoupaDAO();
        if (this.tipoRoupa.getDescricao().length() == 0 || this.tipoRoupa.getPreco() == 0.0 || this.tipoRoupa.getPrazoLavagem() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Preencha os dados da roupa"));
            return null;
        }
        dao.cadastrar(this.tipoRoupa);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Roupa cadastrada com sucesso"));
        return "pedidosPainel?faces-redirect=true";
    }
    
    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public TipoRoupa getTipoRoupa() {
        return tipoRoupa;
    }

    public void setTipoRoupa(TipoRoupa tipoRoupa) {
        this.tipoRoupa = tipoRoupa;
    }
}
