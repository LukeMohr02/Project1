package temp;

import org.w3c.dom.Document;
import orm.commands.*;
import orm.commands.table.condition.Condition;
import orm.exceptions.*;
import orm.utility.FindValidFiles;
import orm.utility.XmlToJava;

import javax.xml.xpath.XPathExpressionException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class XmlToJavaTest {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("Log file");
        XmlToJava xmlToJava = new XmlToJava();

        FindValidFiles fvf = new FindValidFiles("src/main/java/user/xml");
        List<Document> runnableDocuments = fvf.findRunnableDocuments(fvf.getValidDocuments());

        Table tableDDL = null;
        Table tableDML = null;

        for (Document d : runnableDocuments) {
            try {
                tableDDL = xmlToJava.runDDL(d);
                tableDML = xmlToJava.runDML(d);
            } catch (XPathExpressionException | MultipleTagsException | NullAttributeException | InvalidAttributeException | NullTagException | NullContentException | InvalidContentException | ColumnMismatchException | InvalidTypeException e) {
                e.printStackTrace();
            }

        }

        /* DDL */
        System.out.println("<table> exists: " + (tableDDL != null));

        if (tableDDL != null) {
            System.out.println("   table name: " + tableDDL.getName());
            System.out.println("   table schema: " + tableDDL.getSchema());
        }

        System.out.println("   <create> exists: " + (tableDDL.getCreate() != null));

        if (tableDDL.getCreate() != null) {
            System.out.println("      columns: ");
            List<Column> columns = tableDDL.getCreate().getColumns();
            for (Column c : columns) {
                System.out.println(c.getName() + ", " + c.getType() + ", " + c.isPrimaryKey());
            }
        }

        System.out.println("   <alter> exists: " + (tableDDL.getAlter() != null));

        if (tableDDL.getAlter() != null) {
            System.out.println("      <add> exists: " + (tableDDL.getAlter().get(0).getAdd() != null));

            if (tableDDL.getAlter().get(0).getAdd() != null) {
                System.out.println("         columns: ");
                Column[] columns = tableDDL.getAlter().get(0).getAdd().getColumns();
                for (Column c : columns) {
                    System.out.println(c.getName() + ", " + c.getType() + ", " + c.isPrimaryKey());
                }
            }

            System.out.println("      <drop> exists: " + (tableDDL.getAlter().get(0).getDropColumn() != null));
            System.out.println("      <type> exists: " + (tableDDL.getAlter().get(0).getType() != null));

            if (tableDDL.getAlter().get(0).getType() != null) {
                System.out.println("         column name: " + tableDDL.getAlter().get(0).getType().getColumnName());
                System.out.println("         type: " + tableDDL.getAlter().get(0).getType().getType());
            }

            System.out.println("      <constraint> exists: " + (tableDDL.getAlter().get(0).getConstraint() != null));

            if (tableDDL.getAlter().get(0).getConstraint() != null) {
                System.out.println("         constraint: " + tableDDL.getAlter().get(0).getConstraint().getConstraint());
            }
        }

        System.out.println("   <drop> exists: " + (tableDDL.getDrop() != null));

        System.out.println("\n\n\n");

        /* DML */
        System.out.println("<table> exists: " + (tableDML != null));

        if (tableDML != null) {
            System.out.println("   table name: " + tableDDL.getName());
            System.out.println("   table schema: " + tableDDL.getSchema());
        }

        assert tableDML != null;

        System.out.println("   <insert> exists: " + (!tableDML.getInsert().isEmpty()));

        if (!tableDML.getInsert().isEmpty()) {
            System.out.println("      columns: ");
            String[] columns = tableDML.getInsert().get(0).getColumns();
            for (String c : columns) {
                System.out.println(c);
            }
            System.out.println("      values: ");
            String[] values = tableDML.getInsert().get(0).getValues();
            for (String c : values) {
                System.out.println(c);
            }
        }

        System.out.println("   <export> exists: " + (!tableDML.getExport().isEmpty()));

        if (!tableDML.getExport().isEmpty()) {
            System.out.println("      columns: " + Arrays.toString(tableDML.getExport().get(0).getColumns()));

            System.out.println("      <condition> exists: " + (tableDML.getExport().get(0).getConditions().length > 0));

            if (tableDML.getExport().get(0).getConditions().length > 0) {
                System.out.println("         conditions: ");
                Condition[] conditions = tableDML.getExport().get(0).getConditions();
                for (Condition c : conditions) {
                    System.out.println(c.getColumn() + ", " + c.getOperator() + ", " + c.getTargetColumn());
                }
            }
        }

        System.out.println("   <update> exists: " + (!tableDML.getUpdate().isEmpty()));

        if (!tableDML.getUpdate().isEmpty()) {
            System.out.println("      columns: " + Arrays.toString(tableDML.getUpdate().get(0).getColumns()));

            System.out.println("      <condition> exists: " + (tableDML.getUpdate().get(0).getConditions().length > 0));

            if (tableDML.getUpdate().get(0).getConditions().length > 0) {
                System.out.println("         conditions: ");
                Condition[] conditions = tableDML.getUpdate().get(0).getConditions();
                for (Condition c : conditions) {
                    System.out.println(c.getColumn() + ", " + c.getOperator() + ", " + c.getTargetColumn());
                }
            }

            System.out.println("      <values> exists: " + (!tableDML.getUpdate().isEmpty()));

            if (!tableDML.getUpdate().isEmpty()) {
                System.out.println("         values: ");
                String[] values = tableDML.getUpdate().get(0).getValues();
                for (String c : values) {
                    System.out.println(c);
                }
            }

        }

        System.out.println("   <delete> exists: " + (!tableDML.getDelete().isEmpty()));

        if (!tableDML.getDelete().isEmpty()) {
            System.out.println("      <condition> exists: " + (tableDML.getDelete().get(0).getConditions().length > 0));

            if (tableDML.getDelete().get(0).getConditions().length > 0) {
                System.out.println("         conditions: ");
                Condition[] conditions = tableDML.getDelete().get(0).getConditions();
                for (Condition c : conditions) {
                    System.out.println(c.getColumn() + ", " + c.getOperator() + ", " + c.getTargetColumn());
                }
            }
        }
    }
}
