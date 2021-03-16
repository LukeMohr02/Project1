package orm.utility;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import orm.exceptions.MultipleCreateTagsException;

import javax.xml.xpath.*;

public class RunXML {

    XPathFactory xPathFactory;
    XPath xpath;
    XPathExpression xPathExpression;

    public RunXML() {
        xPathFactory = XPathFactory.newInstance();
        xpath = xPathFactory.newXPath();
    }

    private NodeList getNodes(String expression, Document document) throws XPathExpressionException {
        xPathExpression = xpath.compile(expression);
        Object result = xPathExpression.evaluate(document, XPathConstants.NODESET);
        return (NodeList) result;
    }

    protected void run(Document document) throws XPathExpressionException, MultipleCreateTagsException {

        NodeList nl = document.getElementsByTagName("table");
        NodeList nl1;
        NodeList nl2;
        int createCount = 0;

        // <table>
        for (int i = 0; i < nl.getLength(); i++) {
//            System.out.println("i: " + i);
//            System.out.println(nl.item(i).getNodeName());
            nl1 = getNodes("table/*", document);

            for (int j = 0; j < nl1.getLength(); j++) {
//                System.out.println("j: " + j);
//                System.out.println(nl1.item(j).getNodeName());

                // <create>
                if (nl1.item(j).getNodeName().equals("create") && createCount < 1) {
                    createCount++;
                    nl2 = getNodes("table/create[1]/*", document);

                    for (int k = 0; k < nl2.getLength(); k++) {
//                        System.out.println("k: " + k);
//                        System.out.println(nl2.item(k).getNodeName());
                    }
                } else if (nl1.item(j).getNodeName().equals("create") && createCount >= 1) {
                    throw new MultipleCreateTagsException();
                }

            }

        }
    }
}
