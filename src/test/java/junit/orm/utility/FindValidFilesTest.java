package junit.orm.utility;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import orm.utility.FindValidFiles;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class FindValidFilesTest {
    File directory;
    FindValidFiles fvf;
    DocumentBuilder db;
    Document[] expected;

    public FindValidFilesTest() throws ParserConfigurationException, IOException, SAXException {
        directory = new File("src/main/resources/META-INF");
        fvf = new FindValidFiles(directory.getAbsolutePath());
        db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        expected = new Document[]{db.parse(new File("src/main/resources/META-INF/persistence.xml"))};
    }

    @Test
    public void setValidDocumentsTest() {

        fvf.setValidDocuments(fvf.getXmlFiles());

        for (int i = 0; i < fvf.getValidDocuments().length; i++) {
            Assert.assertEquals(fvf.getValidDocuments()[i].getDocumentURI(), expected[i].getDocumentURI());
        }

    }

    @Test
    public void findRunnableDocumentsTest() {
        Assert.assertArrayEquals(fvf.findRunnableDocuments(expected).toArray(), expected);
    }
}
