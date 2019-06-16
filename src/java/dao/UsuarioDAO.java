/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.util.List;
import model.Pedido;
import model.Usuario;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;


public class UsuarioDAO {
   public void cadastrar(Usuario usuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.clear();
        session.save(usuario);
        session.getTransaction().commit();
        session.close();
    }
}
