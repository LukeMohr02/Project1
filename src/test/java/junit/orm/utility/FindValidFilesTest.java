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
        directory = new File("src/test/resources");
        fvf = new FindValidFiles(directory.getAbsolutePath());
        db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        expected = new Document[]{db.parse(new File("src/test/resources/example.xml"))};
    }

    @Test
    public void setValidDocumentsTest() {

        fvf.setValidDocuments(fvf.getXmlFiles());

        for (int i = 0; i < fvf.getValidDocuments().length; i++) {
            Assert.assertEquals(expected[i].getDocumentURI(), fvf.getValidDocuments()[i].getDocumentURI());
        }

    }

    @Test
    public void findRunnableDocumentsTest() {
        Assert.assertArrayEquals(expected, fvf.findRunnableDocuments(expected).toArray());
    }
}
