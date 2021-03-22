package orm.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Properties;

/**
 * Maps objects to/from database
 */
public class ObjectMapping {
    String persistenceUnitName;
    EntityManagerFactory emf;
    EntityManager em;
    EntityTransaction et;

    {
        persistenceUnitName = "Project1";
        emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        em = emf.createEntityManager();
    }

    public ObjectMapping() {

    }

    // if NucleusUserException pops up (multiple copies of persistence API jar in CLASSPATH),
    //      run this: mvn datanucleus:enhance

    //TODO: add connection timeout

    // o --> object to persist
    public void persistObject(Object o) {

        et = em.getTransaction();
        et.begin();
        em.persist(o);
        et.commit();

    }

    // c --> returned object class type
    // o --> object's primary key value
    public Object findObject(Class c, Object o) {
        return em.find(c, o);
    }

    // c --> returned object class type
    // o --> object's primary key value
    public void deleteObject (Class c, Object o) {
        et = em.getTransaction();
        et.begin();
        em.remove(findObject(c, o));
        et.commit();
    }

    public void close() {
        em.close();
        emf.close();
    }
}
