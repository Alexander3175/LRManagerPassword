import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class HibernateHandler {

    private SessionFactory factory;

    public HibernateHandler() {
        factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Password.class).buildSessionFactory();
    }

    public void savePassword(Password password) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.save(password);
        session.getTransaction().commit();
    }

    public List<Password> getAllPasswords() {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        List<Password> passwords = session.createQuery("from Password").getResultList();
        session.getTransaction().commit();
        return passwords;
    }

    public void deletePassword(Long passwordId) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Password password = session.get(Password.class, passwordId);
        if (password != null) {
            session.delete(password);
        }
        session.getTransaction().commit();
    }

    public void close() {
        factory.close();
    }
}
