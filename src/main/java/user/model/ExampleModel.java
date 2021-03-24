package user.model;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "example")
public class ExampleModel implements Comparable<ExampleModel> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int c2;
    private int c3;
    private String cadd;

    public ExampleModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getC2() {
        return c2;
    }

    public void setC2(int c2) {
        this.c2 = c2;
    }

    public int getC3() {
        return c3;
    }

    public void setC3(int c3) {
        this.c3 = c3;
    }

    public String getCadd() {
        return cadd;
    }

    public void setCadd(String cadd) {
        this.cadd = cadd;
    }


    @Override
    public int compareTo(@NotNull ExampleModel m) {
        boolean b = id == m.id;
        if (!b) return 1;

        b = c2 == m.c2;
        if (!b) return 1;

        b = c3 == m.c3;
        if (!b) return 1;

        int i = cadd.compareTo(m.cadd);
        if (i != 0) return i;

        return 0;
    }
}
