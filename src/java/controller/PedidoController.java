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
    private List<ItemPedido> itensPedido;
    private Integer tipoRoupaSelecionada;
    private Map<String, Integer> mapTipoRoupa = new HashMap<String, Integer>();
    private int quantidade;
    
    public PedidoController() {
        if (itensPedido == null) itensPedido = new ArrayList<ItemPedido>();
        if (itemPedido == null) {
            itemPedido = new ItemPedido();
        }
        carregarTipoRoupa();
    }

    public String cadastrar(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // regra para verificar qual o maior prazo e somar valor total
        int maiorPrazo = 0;
        int valorTotal = 0;
        for (ItemPedido ip : itensPedido) {
            if (ip.getTipoRoupa().getPrazoLavagem() > maiorPrazo) {
              maiorPrazo = ip.getTipoRoupa().getPrazoLavagem();  
            }
            // pegar os valores de cada roupa e somar, multiplicando pela qtd
        }
        pedido.setPrazo(maiorPrazo);
        pedido.setValorTotal(500);
        
        // itera sobre itensPedido e salva cada um no pedido
        for (ItemPedido ip : itensPedido) {
            pedido.getRoupasPedido().add(ip);
        }
    
        // finalmente salva o pedido no banco
        session.save(pedido);
        session.getTransaction().commit();
        
        session.close();
        itensPedido.clear();
        return "pedidoRealizado";
    }
    
    public void adicionarItem() {
        if (itemPedido.getQuantidade() > 0) {
            TipoRoupa tipoRoupa = new TipoRoupaDAO().obter(tipoRoupaSelecionada);
            itemPedido.setPedido(pedido);
            itemPedido.setTipoRoupa(tipoRoupa);
            this.itensPedido.add(itemPedido);
            // reseta a variavel para a proxima adição na lista
            this.itemPedido = new ItemPedido();            
        }
        // exibir erro quantidade
        // exibir erro sem roupa selecionada
    }
    
    public void carregarTipoRoupa() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query select = session.createQuery("from TipoRoupa order by id");
        List<TipoRoupa> objetos = (List<TipoRoupa>)select.list();
        for (TipoRoupa tr : objetos) {
            mapTipoRoupa.put(tr.getDescricao(), tr.getId());
        }
        session.getTransaction().commit();
        session.close();
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
