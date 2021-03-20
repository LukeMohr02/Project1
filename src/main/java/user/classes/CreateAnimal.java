package user.classes;

import javax.persistence.*;
import org.datanucleus.enhancer.DataNucleusEnhancer;
import org.datanucleus.api.jpa.metadata.JPAMetaDataManager;
import orm.utility.CustomClassLoader;

public class CreateAnimal {
    public static void main(String[] args) {
        // TODO: move enhancement somewhere else

        String persistenceUnitName = "Project1";
//        DataNucleusEnhancer enhancer = new DataNucleusEnhancer("JPA", null);
//        enhancer.setVerbose(true);
//        enhancer.addPersistenceUnit(persistenceUniteName);
//        enhancer.enhance();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        Animal animal = new Animal();
        animal.setId(1);
        animal.setPhylum("Protozoa");

        et.begin();
        em.persist(animal);
        et.commit();

        em.close();
        emf.close();
    }
}
