package controller;

import dao.PedidoDAO;
import dao.TipoRoupaDAO;
import java.io.IOException;
import model.Pedido;
import model.ItemPedido;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import model.TipoRoupa;

@ManagedBean(name="PedidoController")
@SessionScoped
public class PedidoController {
    @javax.faces.bean.ManagedProperty(value="#{loginMB}")
    private LoginMB loginMB;
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
    
    public void verificarPerfil() {
        if (loginMB.getUsuario().getIdPerfil() != util.Perfil.PERFIL_CLIENTE) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
        if (itensPedido.values().size() == 0) {
            return null;
        }
        pedido = new Pedido();
        // regra para verificar qual o maior prazo e somar valor total
        int maiorPrazo = 0;
        
        for (ItemPedido ip : itensPedido.values()) {
            if (ip.getTipoRoupa().getPrazoLavagem() > maiorPrazo) {
              maiorPrazo = ip.getTipoRoupa().getPrazoLavagem();  
            }
        }
        pedido.setPrazo(maiorPrazo);
        pedido.setValorTotal(valorTotal);
        
        // itera sobre itensPedido e salva cada um no pedido
        for (ItemPedido ip : itensPedido.values()) {
            pedido.getRoupasPedido().add(ip);
            ip.setPedido(pedido);
        }
        
        // finalmente salva o pedido no banco
        new PedidoDAO().cadastrar(pedido);
        
        // limpa as variáveis
        itensPedido = new TreeMap<Integer, ItemPedido>();
        valorTotal = 0.0;
        return "pedidoRealizado";
    }
    
    public void listar() {
        this.pedidos = new PedidoDAO().listar();
    }
    
    public void removerItem(int idTipoRoupa) {
        double valorItem = itensPedido.get(idTipoRoupa).getQuantidade() * itensPedido.get(idTipoRoupa).getTipoRoupa().getPreco();
        valorTotal = valorTotal - valorItem;
        itensPedido.remove(idTipoRoupa);
    }
    
    public void carregarTipoRoupa() {
        listTipoRoupa = new TipoRoupaDAO().listar();
        for (TipoRoupa tr : listTipoRoupa) {
            mapTipoRoupa.put(tr.getDescricao(), tr.getId());
        }
    }

    public Integer getTipoRoupaSelecionada() {
        return tipoRoupaSelecionada;
    }

    public Map<String, Integer> getMapTipoRoupa() {
        return mapTipoRoupa;
    }
    
    public Pedido getPedido() {
        return pedido;
    }

    public Map<Integer,ItemPedido> getItensPedido() {
        return itensPedido;
    }

    public ItemPedido getItemPedido() {
        return itemPedido;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setTipoRoupaSelecionada(Integer tipoRoupaSelecionada) {
        this.tipoRoupaSelecionada = tipoRoupaSelecionada;
    }

    public List<Pedido> getPedidos() {
        listar();
        return this.pedidos;
    }

    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }

}
