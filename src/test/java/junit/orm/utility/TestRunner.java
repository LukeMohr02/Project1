package junit.orm.utility;

import junit.orm.dao.SqlStatementExecutorTest;
import junit.orm.mapping.ObjectMappingTest;
import org.xml.sax.SAXException;
import orm.exceptions.*;
import orm.utility.FindValidFiles;
import orm.utility.JavaToSql;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.sql.SQLException;

public class TestRunner {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, NullContentException, NullAttributeException, InvalidContentException, XPathExpressionException, NullTagException, InvalidAttributeException, MultipleTagsException, InvalidTypeException, ColumnMismatchException, SQLException {
        // FindValidFiles
        FindValidFilesTest fvft = new FindValidFilesTest();
        fvft.setValidDocumentsTest();
        fvft.findRunnableDocumentsTest();

        // XmlToJava
        XmlToJavaTest xtjt = new XmlToJavaTest();
        xtjt.runDDLTest();
        xtjt.runDMLTest();

        // JavaToSql
        JavaToSqlTest jtst = new JavaToSqlTest();
        jtst.findCommandsTest();

        // SqlStatementExecutor
        SqlStatementExecutorTest sset = new SqlStatementExecutorTest();
        sset.executeTest();

        // ObjectMapping
        ObjectMappingTest omt = new ObjectMappingTest();
        omt.deleteObjectTest();
        omt.persistObjectTest();
        omt.findObjectTest();
        omt.closeTest();
    }
}
