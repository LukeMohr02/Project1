package orm.utility;

import org.w3c.dom.Document;
import orm.commands.Table;
import orm.dao.JavaToSql;
import orm.dao.ReadProperties;
import orm.exceptions.*;

import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Driver {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("Log file");
        XmlToJava xmlToJava = new XmlToJava();
        JavaToSql javaToSql = new JavaToSql();

        FindValidFiles fvf = new FindValidFiles("src/main/java/user/xml");
        List<Document> runnableDocuments = fvf.findRunnableDocuments(fvf.getValidDocuments());

        Table tableDDL = null;
        Table tableDML = null;

        for (Document d : runnableDocuments) {
            try {
                tableDDL = xmlToJava.runDDL(d);
                tableDML = xmlToJava.runDML(d);
            } catch (XPathExpressionException | MultipleTagsException | NullAttributeException | InvalidAttributeException | NullTagException | NullContentException | InvalidContentException | ColumnMismatchException e) {
                e.printStackTrace();
            }

        }

        javaToSql.findCommands(tableDDL);
        javaToSql.findCommands(tableDML);
    }
}
