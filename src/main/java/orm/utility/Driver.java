package orm.utility;

import org.w3c.dom.Document;
import orm.commands.Table;
import orm.exceptions.*;

import javax.xml.xpath.XPathExpressionException;
import java.util.List;
import java.util.logging.Logger;

public class Driver {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("Log file");
        XmlToJava xmlToJava = new XmlToJava();

        FindValidFiles fvf = new FindValidFiles("src/main/user/xml");
        List<Document> runnableDocuments = fvf.findRunnableDocuments(fvf.getValidDocuments());

        Table table = null;

        for (Document d : runnableDocuments) {
            try {
                table = xmlToJava.run(d);
            } catch (XPathExpressionException | MultipleTagsException | NullAttributeException | InvalidAttributeException | NullTagException | NullContentException | InvalidContentException e) {
                e.printStackTrace();
            }

        }

    }
}
