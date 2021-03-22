package user.utility;

import orm.dao.ObjectMapping;
import user.classes.Animal;

/**
 * This is an example of a driver that can be created by a user
 */
public class UserDriver {
    public static void main(String[] args) {
        Animal animal = new Animal();
        animal.setType("cat");
        animal.setFavoriteColor("gray");
        animal.setAge(7);

        ObjectMapping om = new ObjectMapping();

        Animal a2 = (Animal) om.findObject(Animal.class, 3);

        System.out.println(a2);

        om.close();
    }
}
