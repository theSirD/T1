package ru.isaev.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.isaev.Entities.Cats.Cat;
import ru.isaev.Entities.Owners.Owner;

import java.util.List;

public class OwnerDAO implements IOwnerDAO {
    final private SessionFactory sessionFactory;

    public OwnerDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Owner owner) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.persist(owner);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error adding owner", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Owner> getAll() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            String sql = "SELECT * FROM OWNER";

            Query query = session.createNativeQuery(sql).addEntity(Owner.class);
            List<Owner> ownerList = query.list();

            transaction.commit();
            return ownerList;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error getting all owners", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Owner getById(Long id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Owner owner = session.find(Owner.class, id);

            transaction.commit();
            return owner;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error getting owner by id", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void update(Owner owner) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.update(owner);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error updating owner", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void remove(Owner owner) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.remove(owner);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error removing owner", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
