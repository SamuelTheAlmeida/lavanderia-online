package controller;

import dao.TipoRoupaDAO;
import model.Pedido;
import model.ItemPedido;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import model.TipoRoupa;
import org.hibernate.Query;
import util.HibernateUtil;
import org.hibernate.Session;

@ManagedBean(name="PedidoController")
@SessionScoped
public class PedidoController {
    private Pedido pedido = new Pedido();
    private List<Pedido> pedidos = new ArrayList<Pedido>();  
    private ItemPedido itemPedido;
    private Map<Integer, ItemPedido> itensPedido;
    private Integer tipoRoupaSelecionada;
    List<TipoRoupa> listTipoRoupa;
    private Map<String, Integer> mapTipoRoupa = new HashMap<String, Integer>();
    private int quantidade;
    private double valorTotal;
    
    public PedidoController() {
        if (itensPedido == null) itensPedido = new TreeMap<Integer, ItemPedido>();
        if (itemPedido == null) {
            itemPedido = new ItemPedido();
        }
        valorTotal = 0.0;
        quantidade = 0;
        carregarTipoRoupa();
        
    }
    
    public void adicionarItem() {
        if (itemPedido.getQuantidade() > 0) {
            TipoRoupa tipoRoupa = new TipoRoupaDAO().obter(tipoRoupaSelecionada);
            
            itemPedido.setPedido(pedido);
            itemPedido.setTipoRoupa(tipoRoupa);
            if (this.itensPedido.containsKey(tipoRoupa.getId())) {
                int qtdAtual = this.itensPedido.get(tipoRoupa.getId()).getQuantidade();
                int qtdAdicionada = itemPedido.getQuantidade();
                this.itensPedido.get(tipoRoupa.getId()).setQuantidade(qtdAtual + qtdAdicionada);
            } else {
                this.itensPedido.put(tipoRoupa.getId(), itemPedido);
            }
            
            // reseta a variavel para a proxima adição na lista
            valorTotal +=  tipoRoupa.getPreco() * itemPedido.getQuantidade();
            this.itemPedido = new ItemPedido();     
        }
        // exibir erro quantidade
        // exibir erro sem roupa selecionada
    }

    public String cadastrar(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        pedido = new Pedido();
        // regra para verificar qual o maior prazo e somar valor total
        int maiorPrazo = 0;
        
        for (ItemPedido ip : itensPedido.values()) {
            if (ip.getTipoRoupa().getPrazoLavagem() > maiorPrazo) {
              maiorPrazo = ip.getTipoRoupa().getPrazoLavagem();  
            }
            //valorTotal += ip.getQuantidade() * ip.getTipoRoupa().getPreco();
        }
        pedido.setPrazo(maiorPrazo);
        pedido.setValorTotal(valorTotal);
        
        // itera sobre itensPedido e salva cada um no pedido
        for (ItemPedido ip : itensPedido.values()) {
            System.out.println(ip.getPedido().getId());
            System.out.println(ip.getTipoRoupa().getId());
            pedido.getRoupasPedido().add(ip);
            ip.setPedido(pedido);
        }
        
        session.clear();
        // finalmente salva o pedido no banco
        System.out.println(pedido);
        session.save(pedido);
        session.getTransaction().commit();
        session.close();
        itensPedido = new TreeMap<Integer, ItemPedido>();
        
        valorTotal = 0.0;
        return "pedidoRealizado";
    }
    
    public void carregarTipoRoupa() {
        listTipoRoupa = new TipoRoupaDAO().listar();
        for (TipoRoupa tr : listTipoRoupa) {
            mapTipoRoupa.put(tr.getDescricao(), tr.getId());
        }
    }
    
    public void removerItem(int idTipoRoupa) {
        double valorItem = itensPedido.get(idTipoRoupa).getQuantidade() * itensPedido.get(idTipoRoupa).getTipoRoupa().getPreco();
        valorTotal = valorTotal - valorItem;
        itensPedido.remove(idTipoRoupa);
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

    public Map<Integer,ItemPedido> getItensPedido() {
        return itensPedido;
    }

    public ItemPedido getItemPedido() {
        return itemPedido;
    }

    public void setItemPedido(ItemPedido itemPedido) {
        this.itemPedido = itemPedido;
    }

    public List<TipoRoupa> getListTipoRoupa() {
        return listTipoRoupa;
    }

    public void setListTipoRoupa(List<TipoRoupa> listTipoRoupa) {
        this.listTipoRoupa = listTipoRoupa;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    
  
}
