package temp;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestDriver {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = db.parse(new File("src/test/java/temp/TestInput.xml"));

        // Gets tag-name
        System.out.println(doc.getDocumentElement().getNodeName());

        // Gets
        NodeList nodeList = doc.getElementsByTagName("tag-name");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            System.out.println("Current element: " + node.getNodeName());

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                System.out.println("All attributes: " + element.getAttributes().item(0) + ", " + element.getAttributes().getNamedItem("type").getTextContent());
                System.out.println("Class attribute: " + element.getAttribute("class"));
                System.out.println("Greeting: " + element.getTextContent());
                System.out.println("Subtag: " + element.getElementsByTagName("sub-tag").item(0).getTextContent());
                System.out.println("Math: " + (Integer.parseInt(element.getElementsByTagName("math").item(0).getTextContent()) + 1));
            }
        }
    }
}
