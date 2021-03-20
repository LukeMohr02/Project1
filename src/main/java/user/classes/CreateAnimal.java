package user.classes;

import javax.persistence.*;
import org.datanucleus.enhancer.DataNucleusEnhancer;
import org.datanucleus.api.jpa.metadata.JPAMetaDataManager;
import orm.utility.CustomClassLoader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class CreateAnimal {
    public static void main(String[] args) throws IOException {
        // TODO: if NucleusUserException pops up (multiple copies of persistence API jar in CLASSPATH),
        //      run this: mvn datanucleus:enhance

        String persistenceUnitName = "Project1";
//        DataNucleusEnhancer enhancer = new DataNucleusEnhancer("JPA", null);
//        enhancer.setVerbose(true);
//        enhancer.addPersistenceUnit(persistenceUniteName);
//        enhancer.enhance();

        Properties properties = new Properties();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        Animal animal = new Animal();
        animal.setId(1);
        animal.setPhylum("Protozoa");
        animal.setColor("Red");

        et.begin();
        em.persist(animal);
        et.commit();

        em.close();
        emf.close();
    }
}
