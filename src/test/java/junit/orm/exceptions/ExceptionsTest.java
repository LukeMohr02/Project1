package junit.orm.exceptions;

import org.junit.Assert;
import org.junit.Test;
import orm.exceptions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExceptionsTest {

    @Test
    public void testExceptions() {

        Assert.assertThrows(ColumnMismatchException.class, () -> {
            throw new ColumnMismatchException(new String[]{"dummyColumn1,dummyColumn2"}, new String[]{"dummyValue1","dummyValue2"});
        });

        Assert.assertThrows(InvalidAttributeException.class, () -> {
            throw new InvalidAttributeException("dummyAttribute","dummyTag");
        });

        Assert.assertThrows(InvalidAttributeException.class, () -> {
            throw new InvalidAttributeException("dummyAttribute","dummyTag", new ArrayList<String>(Arrays.asList("dummyValue1","dummyValue2")));
        });

        Assert.assertThrows(InvalidConstraintException.class, () -> {
            throw new InvalidConstraintException("dummyMessage");
        });

        Assert.assertThrows(InvalidContentException.class, () -> {
            throw new InvalidContentException();
        });

        Assert.assertThrows(InvalidTypeException.class, () -> {
            throw new InvalidTypeException();
        });

        Assert.assertThrows(MultipleTagsException.class, () -> {
            throw new MultipleTagsException("dummyTag");
        });

        Assert.assertThrows(NullAttributeException.class, () -> {
            throw new NullAttributeException("dummyAttribute");
        });

        Assert.assertThrows(NullContentException.class, () -> {
            throw new NullContentException();
        });

        Assert.assertThrows(NullTagException.class, () -> {
            throw new NullTagException("dummyTag");
        });
    }

}
