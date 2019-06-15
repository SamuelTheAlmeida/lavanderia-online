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
}
