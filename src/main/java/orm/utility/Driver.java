package orm.utility;

import org.w3c.dom.Document;
import orm.commands.Table;
import orm.dao.SqlStatementExecutor;
import orm.dao.JavaToSql;
import orm.exceptions.*;

import javax.xml.xpath.XPathExpressionException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class Driver {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("Log file");
        XmlToJava xmlToJava = new XmlToJava();
        JavaToSql javaToSql = new JavaToSql();
        SqlStatementExecutor executor = new SqlStatementExecutor();

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

        String[] DDLCommands = javaToSql.findCommands(tableDDL);
        String[] DMLCommands = javaToSql.findCommands(tableDML);


        try {
            executor.execute(DDLCommands);
            executor.execute(DMLCommands);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
