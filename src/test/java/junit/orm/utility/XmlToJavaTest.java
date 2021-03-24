package junit.orm.utility;

import junit.orm.command.GetTable;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import orm.commands.Table;
import orm.commands.table.Alter;
import orm.dao.SqlStatementExecutor;
import orm.exceptions.*;
import orm.utility.JavaToSql;
import orm.utility.XmlToJava;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.sql.SQLException;

public class XmlToJavaTest {
    XmlToJava xtj = new XmlToJava();
    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    Document exampleDocument;
    Table ddlTable;
    Table dmlTable;

    public XmlToJavaTest() throws ParserConfigurationException, IOException, SAXException, InvalidTypeException, ColumnMismatchException {
        exampleDocument = db.parse("src/test/resources/example.xml");
        ddlTable = GetTable.generateDdlTable();
        dmlTable = GetTable.generateDmlTable();
    }

    @Test
    public void runDDLTest() throws NullAttributeException, InvalidAttributeException, XPathExpressionException, NullTagException, MultipleTagsException, NullContentException, InvalidTypeException, InvalidContentException, SQLException {
        Assert.assertEquals(xtj.runDDL(exampleDocument).compareTo(ddlTable), 0);
    }

    @Test
    public void runDMLTest() throws NullAttributeException, ColumnMismatchException, InvalidAttributeException, XPathExpressionException, NullTagException, MultipleTagsException, NullContentException, InvalidContentException {
        Assert.assertEquals(xtj.runDML(exampleDocument).compareTo(dmlTable), 0);
    }
}
