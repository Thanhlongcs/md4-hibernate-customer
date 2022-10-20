package long_nguyen.service;

import long_nguyen.model.Customer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
@Transactional
public class CustomerServiceIMPL implements ICustomerService{
    public static SessionFactory sessionFactory;
    public static EntityManager entityManager;
    static {
        try{
            sessionFactory = new Configuration().configure("hibernate.conf.xml").buildSessionFactory();
            entityManager=sessionFactory.createEntityManager();
        }catch (HibernateException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Customer> findAll() {
        String queryStr = "SELECT c FROM Customer AS c";
        TypedQuery<Customer> query = entityManager.createQuery(queryStr, Customer.class);
        return query.getResultList();
    }

    @Override
    public void save(Customer customer) {
        Session session = null;
        Transaction transaction = null;
        try{
            session=sessionFactory.openSession();
            transaction=session.beginTransaction();
            session.saveOrUpdate(customer);
            transaction.commit();
        }catch(HibernateException e){
            e.printStackTrace();
            if(transaction!=null){
                transaction.rollback();
            }
        }
    }

    @Override
    public Customer findById(Long id) {
        String queryStr = "select c from Customer as c where c.id= :id";
        TypedQuery<Customer> query = entityManager.createQuery(queryStr, Customer.class);
        query.setParameter("id",id);
        return query.getSingleResult();
    }

    @Override
    public void deleteById(Long id) {
        Session session = null;
        Transaction transaction = null;
        try{
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.delete(findById(id));
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
            if (transaction!=null){
                session.close();
            }
        }finally {
            if (session!=null){
                session.close();
            }
        }

    }
}
