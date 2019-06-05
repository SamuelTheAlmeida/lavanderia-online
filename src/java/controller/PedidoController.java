/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Pedido;
import model.ItemPedido;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import model.ItemPedidoId;
import model.TipoRoupa;
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
    private Integer tipoRoupaSelecionada;
    private Map<String, Integer> mapTipoRoupa = new HashMap<String, Integer>();
    private int quantidade;
    
    public PedidoController() {
        if (itensPedido == null) itensPedido = new ArrayList<ItemPedido>();
        if (itemPedido == null) {
            itemPedido = new ItemPedido();
        }
        mapTipoRoupa.put("Camiseta", 1);
        mapTipoRoupa.put("Blusa", 2);
        mapTipoRoupa.put("Calça", 3);
        mapTipoRoupa.put("Casaco", 4);
        mapTipoRoupa.put("Blusão", 5);
        mapTipoRoupa.put("Sobretudo", 6);
        mapTipoRoupa.put("Meias", 7);
        mapTipoRoupa.put("Jaqueta", 8);
        mapTipoRoupa.put("Jeans", 9);
        mapTipoRoupa.put("Saia", 10);
        mapTipoRoupa.put("Vestido", 11);
        mapTipoRoupa.put("Bermuda", 12);
        mapTipoRoupa.put("Short", 13);  
    }

    public String cadastrar(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // implementar regra de negocio para verificar qual o maior prazo
        pedido.setPrazo(10);
        pedido.setValorTotal(500);

        // tipo roupa deve vir do banco
        TipoRoupa tipoRoupa = itensPedido.get(0).getTipoRoupa();
        //new category, need save to get the id first
        session.save(tipoRoupa);
        
        // iterar sobre itensPedido e salvar cada um
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setPedido(pedido);
        itemPedido.setTipoRoupa(tipoRoupa);
        itemPedido.setQuantidade(2);
        pedido.getRoupasPedido().add(itemPedido);
        session.save(pedido);
        session.getTransaction().commit();
        
        
        return "pedidoRealizado";
        //return "pedidos";
    }
    
    public void adicionarItem() {
        if (itemPedido.getQuantidade() > 0) {
            TipoRoupa tipoRoupa = new TipoRoupa();
            tipoRoupa.setId(tipoRoupaSelecionada);
            String[] lista = Arrays.copyOf(mapTipoRoupa.keySet().toArray(), mapTipoRoupa.keySet().toArray().length, String[].class);
            tipoRoupa.setDescricao(lista[0]);
            tipoRoupa.setPrazoLavagem(10);
            itemPedido.setPedido(pedido);
            itemPedido.setTipoRoupa(tipoRoupa);
            this.itensPedido.add(itemPedido);
            this.itemPedido = new ItemPedido();            
        }
    }
    
    public void removerItem() {
        //itensPedido.remove(itensPedido)
    }
    
    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getTipoRoupaSelecionada() {
        return tipoRoupaSelecionada;
    }

    public void setTipoRoupaSelecionada(Integer tipoRoupaSelecionada) {
        this.tipoRoupaSelecionada = tipoRoupaSelecionada;
    }

    public Map<String, Integer> getMapTipoRoupa() {
        return mapTipoRoupa;
    }

    public void setMapTipoRoupa(Map<String, Integer> mapTipoRoupa) {
        this.mapTipoRoupa = mapTipoRoupa;
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
  
}
