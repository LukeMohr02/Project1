package orm.utility;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.*;

import javax.xml.parsers.*;
import java.io.File;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Finds all XML files in a given directory that can be processed by the framework
 */
public class FindValidFiles {
    DocumentBuilder db;
    Document[] validDocuments;
    File directory;
    File[] xmlFiles;

    public FindValidFiles(String dir) {
        this.directory = new File(dir);

        // Finds all XML files in a given directory
        xmlFiles = directory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".xml");
            }
        });

        try {
            db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        setValidDocuments(xmlFiles);
    }

    public Document[] getValidDocuments() {
        return validDocuments;
    }

    // Parses XML files
    public void setValidDocuments(File[] xmlFiles) {
        Document[] tempDocuments = new Document[xmlFiles.length];

        for (int i = 0; i < xmlFiles.length; i++) {
            try {
                Document doc = db.parse(xmlFiles[i]);
                tempDocuments[i] = doc;
            } catch (SAXException | IOException e) {
                e.printStackTrace();
            }
        }

        validDocuments = tempDocuments;
    }

    // Searches input Documents for <run> tag, returns Documents in a list (in order of run priority)
    public List<Document> findRunnableDocuments(Document[] inputDocuments) {
        List<Document> documents = new ArrayList<>();
        List<Integer> runPriority = new ArrayList<>();

        // Searches documents for <run> tag and priority="" attribute
        for (Document d : inputDocuments) {
            try {
                d.getElementsByTagName("run").item(0).getNodeName();
                documents.add(d);

                try {
                    runPriority.add(Integer.parseInt(d.getElementsByTagName("run").item(0).getAttributes().getNamedItem("priority").getTextContent()));
                } catch (NullPointerException | NumberFormatException e) {
                    runPriority.add(0);
                }

            } catch (NullPointerException e) {
                // Exception is ignored
            }
        }

        // Sorts Document list by run priority
        int tempInt;
        Document tempDocument;
        for (int i = 0; i < runPriority.size(); i++) {
            for (int j = i + 1; j < runPriority.size(); j++) {

                if (runPriority.get(i) < runPriority.get(j)) {
                    tempInt = runPriority.get(i);
                    runPriority.set(i, runPriority.get(j));
                    runPriority.set(j, tempInt);

                    tempDocument = documents.get(i);
                    documents.set(i, documents.get(j));
                    documents.set(j, tempDocument);
                }
            }
        }



        return documents;
    }

    public File[] getXmlFiles() {
        return xmlFiles;
    }


}
