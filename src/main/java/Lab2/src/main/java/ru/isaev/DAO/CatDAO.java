package ru.isaev.DAO;

import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.isaev.Entities.Cats.Cat;

import java.util.List;

public class CatDAO implements ICatDAO {
    final private SessionFactory sessionFactory;

    public CatDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Cat cat) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.persist(cat);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error adding cat", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Cat> getAll() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            String sql = "SELECT * FROM cats";

            Query query = session.createNativeQuery(sql).addEntity(Cat.class);
            List<Cat> catList = query.getResultList();

            transaction.commit();
            return catList;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error getting all cats", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Cat getById(Long id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Cat cat = session.find(Cat.class, id);

            transaction.commit();
            return cat;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error getting cat by id", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void update(Cat cat) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.update(cat);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error updating cat", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void remove(Cat cat) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.remove(cat);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error removing cat", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
