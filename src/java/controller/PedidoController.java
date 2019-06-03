/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Pedido;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.hibernate.Query;
import util.HibernateUtil;
import org.hibernate.Session;

/**
 *
 * @author SAMUEL
 */
@ManagedBean(name="PedidoController")
@SessionScoped
public class PedidoController {
    private Pedido pedido = new Pedido();
    private List<Pedido> pedidos = new ArrayList<Pedido>();  
    private ItemPedido itemPedido;
    private List<ItemPedido> itensPedido;
    private String tipoRoupaSelecionada;
    private List<String> listTipoRoupa = new ArrayList<String>();
    private int quantidade;
    
    public PedidoController() {
        if (itensPedido == null) itensPedido = new ArrayList<ItemPedido>();
        if (itemPedido == null) itemPedido = new ItemPedido();
        listTipoRoupa.add("Camiseta");
        listTipoRoupa.add("Blusa");
        listTipoRoupa.add("Calça");
        listTipoRoupa.add("Casaco");
        listTipoRoupa.add("Blusão");
        listTipoRoupa.add("Sobretudo");
        listTipoRoupa.add("Meias");
        listTipoRoupa.add("Jaqueta");
        listTipoRoupa.add("Jeans");
        listTipoRoupa.add("Saia");
        listTipoRoupa.add("Vestido");
        listTipoRoupa.add("Bermuda");
        listTipoRoupa.add("Short");  
    }

    public String cadastrar(){
        Session session = HibernateUtil.getSessionFactory().
        openSession();
        session.beginTransaction();
        session.save(pedido);
        session.getTransaction().commit();
        return null;
        //return "pedidos";
    }
    
    public void adicionarItem() {
        if (itemPedido.quantidade > 0) {
            this.itensPedido.add(itemPedido);
            this.itemPedido = new ItemPedido();            
        }
    }
    
    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getTipoRoupaSelecionada() {
        return tipoRoupaSelecionada;
    }

    public void setTipoRoupaSelecionada(String tipoRoupaSelecionada) {
        this.tipoRoupaSelecionada = tipoRoupaSelecionada;
    }

    public List<String> getListTipoRoupa() {    
        return listTipoRoupa;
    }

    public void setListTipoRoupa(List<String> listTipoRoupa) {
        this.listTipoRoupa = listTipoRoupa;
    }
    
    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public List<ItemPedido> getItensPedido() {
        return itensPedido;
    }

    public ItemPedido getItemPedido() {
        return itemPedido;
    }

    public void setItemPedido(ItemPedido itemPedido) {
        this.itemPedido = itemPedido;
    }
    
    public class ItemPedido {
        private String nome;
        private int quantidade;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public int getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }
        
    }
}
