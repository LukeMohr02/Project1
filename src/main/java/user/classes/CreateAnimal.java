package user.classes;

import javax.persistence.*;

public class CreateAnimal {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Animal_JPA");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Animal animal = new Animal();
        animal.setId(1);
        animal.setPhylum("Protozoa");

        em.persist(animal);
        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}
