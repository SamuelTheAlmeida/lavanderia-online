package controller;

import dao.PedidoDAO;
import dao.TipoRoupaDAO;
import dao.UsuarioDAO;
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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import model.Cliente;
import model.TipoRoupa;
import util.Constantes;

@ManagedBean(name="PedidoController")
@SessionScoped
public class PedidoController {
    @javax.faces.bean.ManagedProperty(value="#{loginMB}")
    private LoginMB loginMB;
    private Pedido pedido = new Pedido();
    private Integer numPedidoPesquisa;
    private List<Pedido> pedidos = new ArrayList<Pedido>(); 
    private DataModel<Pedido> pedidosDataModel;
    
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
        System.out.println("idusuario: " + this.loginMB.getUsuario().getId());
        Cliente c = new UsuarioDAO().obterCliente(this.loginMB.getUsuario().getId());
        //System.out.println("cliente: " + c.getId());
        pedido.setCliente(c);
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
        return "detalhesPedido?faces-redirect=true";
    }
    
    public void listar() {
        if (this.loginMB.isCliente()) {
            Cliente c = new UsuarioDAO().obterCliente(this.loginMB.getUsuario().getId());
            if (this.numPedidoPesquisa != null) {
                this.pedidos = new PedidoDAO().listarPorCliente(c.getId(), this.numPedidoPesquisa);
            } else {
                this.pedidos = new PedidoDAO().listarPorCliente(c.getId());
            }              
        } else {
            if (this.numPedidoPesquisa != null) {
                this.pedidos = new PedidoDAO().listar(this.numPedidoPesquisa);
            } else {
                this.pedidos = new PedidoDAO().listar();
            }            
        }

        pedidosDataModel = new ListDataModel<Pedido>(this.pedidos);
    }
    
    public void limparFiltros() {
        this.numPedidoPesquisa = null;
        listar();
    }
    
    public String detalhesPedido() {
        Pedido pedidoClicado = pedidosDataModel.getRowData();
        Pedido pedido = new PedidoDAO().obter(pedidoClicado.getId());
        if (pedido != null) {
            this.pedido = pedido;
            return "detalhesPedido?faces-redirect=true";
        } else {
            return null;
        }
    }
    
    public String cancelarPedido() {
        Pedido pedidoClicado = pedidosDataModel.getRowData();
        Pedido pedido = new PedidoDAO().obter(pedidoClicado.getId());
        if (pedido.getIdStatus() != Constantes.STATUS_PENDENTE_PAGAMENTO) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Só é possivel cancelar pedidos pendentes!"));                        
            return null;
        } else {
            new PedidoDAO().remover(pedido);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pedido " + pedido.getId() + " cancelado com sucesso!"));            
            return null;
        }
    }
    
    
    public String confirmarPagamento() {
        Pedido pedidoClicado = pedidosDataModel.getRowData();
        Pedido pedido = new PedidoDAO().obter(pedidoClicado.getId());
        if (pedido != null) {
            this.pedido = pedido;
            this.pedido.setIdStatus(util.Constantes.STATUS_EM_ANDAMENTO);
            new PedidoDAO().alterarStatus(this.pedido);
        }
        return null;
    }
    
    public String confirmarLavagem() {
        Pedido pedidoClicado = pedidosDataModel.getRowData();
        Pedido pedido = new PedidoDAO().obter(pedidoClicado.getId());
        if (pedido != null) {
            this.pedido = pedido;
            this.pedido.setIdStatus(util.Constantes.STATUS_AGUARDANDO_ENTREGA);
            new PedidoDAO().alterarStatus(this.pedido);
        }
        return null;        
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

    public DataModel<Pedido> getPedidosDataModel() {
        listar();
        return pedidosDataModel;
    }

    public void setPedidosDataModel(DataModel<Pedido> pedidosDataModel) {
        this.pedidosDataModel = pedidosDataModel;
    }

    public Integer getNumPedidoPesquisa() {
        return numPedidoPesquisa;
    }

    public void setNumPedidoPesquisa(Integer numPedidoPesquisa) {
        this.numPedidoPesquisa = numPedidoPesquisa;
    }
    
}
