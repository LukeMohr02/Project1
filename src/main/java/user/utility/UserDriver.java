package user.utility;

import orm.dao.ObjectMapping;
import user.classes.Animal;

public class UserDriver {
    public static void main(String[] args) {
        Animal animal = new Animal();
        animal.setColor("blue");
        animal.setPhylum("Protozoa");
        animal.setId(5);

        ObjectMapping om = new ObjectMapping();

        om.persist(animal);
    }
}
