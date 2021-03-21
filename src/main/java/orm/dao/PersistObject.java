package orm.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Properties;

public class PersistObject {
    // TODO: if NucleusUserException pops up (multiple copies of persistence API jar in CLASSPATH),
    //      run this: mvn datanucleus:enhance

    //TODO: add connection timeout

    public void persist(Object o) {
        String persistenceUnitName = "Project1";

//      Properties properties = new Properties();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        et.begin();
        em.persist(o);
        et.commit();

        em.close();
        emf.close();
    }
}
