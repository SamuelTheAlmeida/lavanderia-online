/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.TipoRoupa;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author s123
 */
public class TipoRoupaDAO {
    public TipoRoupa obter(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query select = session.createQuery("from TipoRoupa where id = " + id);
        TipoRoupa result = (TipoRoupa)select.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
    }
    
    public List<TipoRoupa> listar() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query select = session.createQuery("from TipoRoupa order by id");
        List<TipoRoupa> lista = (List<TipoRoupa>)select.list();
        session.getTransaction().commit();
        session.close();
        return lista;
    }
}
