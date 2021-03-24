package junit.orm.mapping;

import user.model.ExampleModel;
import org.junit.Assert;
import org.junit.Test;
import orm.mapping.ObjectMapping;

public class ObjectMappingTest {

    ObjectMapping om = new ObjectMapping();
    ExampleModel m = new ExampleModel();

    public ObjectMappingTest() {
        m.setId(999);
        m.setC2(5);
        m.setC3(19);
        m.setCadd("text column");
    }

    @Test
    public void persistObjectTest() {
        om.persistObject(m);
        Assert.assertEquals(m.compareTo((ExampleModel) om.findObject(ExampleModel.class, m.getId())), 0);
    }

    @Test
    public void findObjectTest() {
        // TODO: this
//        Assert.assertEquals(om.findObject(m.getId()).equals(m));
    }

    @Test
    public void deleteObjectTest() {

    }

    @Test
    public void closeTest() {
        om.close();
    }
}
