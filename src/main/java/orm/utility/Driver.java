package orm.utility;

import org.w3c.dom.Document;
import orm.commands.Table;
import orm.dao.SqlStatementExecutor;
import orm.exceptions.*;

import javax.xml.xpath.XPathExpressionException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class Driver {
    public static void main(String[] args) {
        XmlToJava xmlToJava = new XmlToJava();
        JavaToSql javaToSql = new JavaToSql();
        SqlStatementExecutor executor = new SqlStatementExecutor();
        Logger logger = Logger.getLogger("Log file");
//        PropertyConfigurator.configure("C:\\Users\\Luke\\Desktop\\Computer Science\\gitrepos\\Project1\\src\\main\\resources\\log4j.properties");

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

        String[] DDLCommands = javaToSql.findCommands(tableDDL);
        String[] DMLCommands = javaToSql.findCommands(tableDML);


        try {
            executor.execute(DDLCommands);
            executor.execute(DMLCommands);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
