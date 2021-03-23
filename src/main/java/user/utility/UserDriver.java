package user.utility;

import orm.mapping.ObjectMapping;
import user.classes.Animal;
import user.classes.Classification;

/**
 * This is an example of a driver that can be created by a user
 */
public class UserDriver {
    public static void main(String[] args) {
        Animal animal = new Animal();
        animal.setType("alligator");
        animal.setFavoriteColor("green");
        animal.setAge(17);

        ObjectMapping om = new ObjectMapping();

//        om.persistObject(animal);
//        om.deleteObject(Animal.class, 5);
//        Animal a2 = (Animal) om.findObject(Animal.class, 3);

//        System.out.println(a2);


        Classification classification = new Classification();
        classification.setAnimal(animal);
        classification.setPhylum("Chordata");
        classification.setClazz("Reptilia");
        classification.setOrder("Crocodilia");
        classification.setFamily("Alligatoridae");
        classification.setGenus("Alligator");
        classification.setSpecies("Alligator mississippiensis");

        om.persistObject(classification);

        om.close();
    }
}
