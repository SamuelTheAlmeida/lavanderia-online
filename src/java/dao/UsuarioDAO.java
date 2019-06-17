/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.util.List;
import model.Cliente;
import model.Funcionario;
import model.Pedido;
import model.Usuario;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;


public class UsuarioDAO {
   public void cadastrarUsuario(Usuario usuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.clear();
        session.save(usuario);
        session.getTransaction().commit();
        session.close();
    }
   
    public void cadastrarCliente(Cliente cliente) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.clear();
        session.save(cliente);
        session.getTransaction().commit();
        session.close();
    }
    
    public void cadastrarFuncionario(Funcionario funcionario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.clear();
        session.save(funcionario);
        session.getTransaction().commit();
        session.close();
    }    
   
   public boolean cpfExiste(String cpf) {
        System.out.println(cpf);
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.clear();
        Query select = session.createQuery("from Cliente where cpf = ?");
        select.setString(0, cpf);
        List<Cliente> lista = select.list();
        session.getTransaction().commit();
        session.close();
        return lista.size() > 0;
   }
   
   public boolean emailExiste(String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.clear();
        Query select = session.createQuery("from Usuario where email = ?");
        select.setString(0, email);
        List<Cliente> lista = select.list();
        session.getTransaction().commit();
        session.close();
        return lista.size() > 0;
   }
   
   public Usuario autenticar(Usuario usuario) {
        boolean result = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.clear();
        Query select = session.createQuery("from Usuario where email = ? and senha = ?");
        select.setString(0, usuario.getEmail());
        select.setString(1, usuario.getSenha());
        Usuario userResult = (Usuario)select.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return userResult;
   }
   
   
}
