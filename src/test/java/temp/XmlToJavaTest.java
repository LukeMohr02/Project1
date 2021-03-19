package temp;

import org.w3c.dom.Document;
import orm.commands.*;
import orm.exceptions.*;
import orm.utility.FindValidFiles;
import orm.utility.XmlToJava;

import javax.xml.xpath.XPathExpressionException;
import java.util.List;
import java.util.logging.Logger;

public class XmlToJavaTest {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("Log file");
        XmlToJava xmlToJava = new XmlToJava();

        FindValidFiles fvf = new FindValidFiles("src/main/user/xml");
        List<Document> runnableDocuments = fvf.findRunnableDocuments(fvf.getValidDocuments());

        Table table = null;

        for (Document d : runnableDocuments) {
            try {
                table = xmlToJava.run(d);
            } catch (XPathExpressionException | MultipleTagsException | NullAttributeException | InvalidAttributeException | NullTagException | NullContentException | InvalidContentException e) {
                e.printStackTrace();
            }

        }

        System.out.println("<table> exists: " + (table != null));

        if (table != null) {
            System.out.println("   table name: " + table.getName());
            System.out.println("   table schema: " + table.getSchema());
        }

        System.out.println("   <create> exists: " + (table.getCreate() != null));

        if (table.getCreate() != null) {
            System.out.println("      columns: ");
            List<Column> columns = table.getCreate().getColumns();
            for (Column c : columns) {
                System.out.println(c.getName() + ", " + c.getType() + ", " + c.isPrimaryKey());
            }
        }

        System.out.println("   <alter> exists: " + (table.getAlter() != null));

        if (table.getAlter() != null) {
            System.out.println("      <add> exists: " + (table.getAlter().get(0).getAdd() != null));

            if (table.getAlter().get(0).getAdd() != null) {
                System.out.println("         columns: ");
                List<Column> columns = table.getAlter().get(0).getAdd().getColumns();
                for (Column c : columns) {
                    System.out.println(c.getName() + ", " + c.getType() + ", " + c.isPrimaryKey());
                }
            }

            System.out.println("      <drop> exists: " + (table.getAlter().get(0).getDropColumn() != null));
            System.out.println("      <type> exists: " + (table.getAlter().get(0).getType() != null));

            if (table.getAlter().get(0).getType() != null) {
                System.out.println("         column name: " + table.getAlter().get(0).getType().getColumnName());
                System.out.println("         type: " + table.getAlter().get(0).getType().getType());
            }

            System.out.println("      <constraint> exists: " + (table.getAlter().get(0).getConstraint() != null));

            if (table.getAlter().get(0).getConstraint() != null) {
                System.out.println("         constraint: " + table.getAlter().get(0).getConstraint().getName());
            }
        }

        System.out.println("   <drop> exists: " + (table.getDrop() != null));
    }
}
