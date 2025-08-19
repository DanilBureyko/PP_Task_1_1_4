package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getConnectionHibernate();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS pp_task1.users " +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), " +
                    " lastname VARCHAR(255), age INT)").executeUpdate();
            transaction.commit();
        } catch (HibernateException e){
            if( transaction!=null ){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            session.createSQLQuery("DROP TABLE IF EXISTS `pp_task1`.`users`").addEntity(User.class).executeUpdate();
            transaction.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            session.save(new User(name,lastName,age));
            transaction.commit();
        }catch (HibernateException e){
            if( transaction!=null ){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally{
            session.close();
        }

    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            session.delete(session.get(User.class,id));
            transaction.commit();
        }catch (HibernateException e){
            if (transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<User> userList = session.createQuery("from User").getResultList();
        try{
            transaction.commit();
            return userList;
        } catch (HibernateException e){
            if( transaction!=null ){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            session.createSQLQuery("TRUNCATE TABLE `pp_task1`.`users`").addEntity(User.class).executeUpdate();
            transaction.commit();
        }catch (HibernateException e){
            if( transaction!=null ){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
}

