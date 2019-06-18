/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Pedido;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author SAMUEL
 */
public class PedidoDAO {
    public void cadastrar(Pedido pedido) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.clear();
        session.save(pedido);
        session.getTransaction().commit();
        session.close();
    }
    
    public List<Pedido> listar() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.clear();
        Query select = session.createQuery("from Pedido");
        List<Pedido> lista = select.list();
        session.getTransaction().commit();
        session.close();
        return lista;
    }
    
    public List<Pedido> listar(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.clear();
        Query select = session.createQuery("from Pedido where id = :id");
        select.setInteger("id", id);
        List<Pedido> lista = select.list();
        session.getTransaction().commit();
        session.close();
        return lista;
    }  

    public List<Pedido> listarPorCliente(int idCliente) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.clear();
        Query select = session.createQuery("from Pedido where cliente_id = :idCliente");
        select.setInteger("idCliente", idCliente);
        List<Pedido> lista = select.list();
        session.getTransaction().commit();
        session.close();
        return lista;
    }
    
    public List<Pedido> listarPorCliente(int idCliente, int idPedido) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.clear();
        Query select = session.createQuery("from Pedido where id = :idPedido AND cliente_id = :idCliente");
        select.setInteger("idPedido", idPedido);
        select.setInteger("idCliente", idCliente);
        List<Pedido> lista = select.list();
        session.getTransaction().commit();
        session.close();
        return lista;
    }    
    
    public Pedido obter(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.clear();
        Query select = session.createQuery("from Pedido where id = :id");
        select.setInteger("id", id);
        Pedido pedido = (Pedido)select.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return pedido;
    }
    
    public void remover(Pedido pedido) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.clear();
        session.delete(pedido);
        session.getTransaction().commit();
        session.close();
    }
    
    public void alterarStatus(Pedido pedido) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(pedido);
        session.getTransaction().commit();
        session.close();        
    }
}
