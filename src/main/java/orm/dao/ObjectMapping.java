package orm.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Properties;

public class ObjectMapping {
    String persistenceUnitName;
    EntityManagerFactory emf;
    EntityManager em;
    EntityTransaction et;

    {
        persistenceUnitName = "Project1";
        emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        em = emf.createEntityManager();
        et = em.getTransaction();
    }

    public ObjectMapping() {

    }

    // if NucleusUserException pops up (multiple copies of persistence API jar in CLASSPATH),
    //      run this: mvn datanucleus:enhance

    //TODO: add connection timeout

    public void persist(Object o) {

//      Properties properties = new Properties();


        et.begin();
        em.persist(o);
        et.commit();

        em.close();
        emf.close();
    }
}
