package orm.utility;

import org.w3c.dom.Document;
import orm.exceptions.MultipleCreateTagsException;
import orm.utility.FindValidFiles;
import orm.utility.RunXML;

import javax.xml.xpath.XPathExpressionException;
import java.util.List;
import java.util.logging.Logger;

public class Driver {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("Log file");
        RunXML runXML = new RunXML();

        FindValidFiles fvf = new FindValidFiles("src/main/resources");
        List<Document> runnableDocuments = fvf.findRunnableDocuments(fvf.getValidDocuments());



        for (Document d : runnableDocuments) {

            try {
                runXML.run(d);
            } catch (XPathExpressionException | MultipleCreateTagsException e) {
                e.printStackTrace();
            }

        }



    }
}
