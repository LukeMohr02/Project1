package user.classes;

import javax.persistence.*;

@Entity
@Table
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String phylum;

    public Animal() {

    }

    public Animal(int id, String phylum) {
        this.id = id;
        this.phylum = phylum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhylum() {
        return phylum;
    }

    public void setPhylum(String phylum) {
        this.phylum = phylum;
    }

    @Override
    public String toString() {
        return "Animal with id " + id + " and phylum " + phylum;
    }
}
