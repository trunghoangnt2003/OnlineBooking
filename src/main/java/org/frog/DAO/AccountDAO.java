package org.frog.DAO;

import org.frog.model.Account;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.UUID;

public class AccountDAO {

    public void save(Account account) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            // save the student object
            session.save(account);
            // commit transaction
            transaction.commit();
        }catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public Account getAccountById(UUID id) {

        Transaction transaction = null;
        Account user = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // get an user object
            user = session.get(Account.class, id);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return user;
    }
}
