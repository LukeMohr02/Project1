package orm.utility;

import org.w3c.dom.*;
import orm.commands.*;
import orm.commands.table.*;
import orm.commands.table.alter.*;
import orm.exceptions.*;

import javax.xml.xpath.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XmlToJava {

    XPathFactory xPathFactory;
    XPath xpath;
    XPathExpression xPathExpression;

    List<String> validColumnTypes = new ArrayList<>(Arrays.asList("text", "int", "auto-int", "decimal", "money", "bool"));
    List<String> validConstraints = new ArrayList<>(Arrays.asList("not-empty", "unique", "unique-id"));

    public XmlToJava() {
        xPathFactory = XPathFactory.newInstance();
        xpath = xPathFactory.newXPath();
    }

    private NodeList getNodes(String expression, Document document) throws XPathExpressionException {
        xPathExpression = xpath.compile(expression);
        Object result = xPathExpression.evaluate(document, XPathConstants.NODESET);
        return (NodeList) result;
    }

    // TODO: make custom exceptions more descriptive
    public Table run(Document document) throws XPathExpressionException, MultipleTagsException, NullAttributeException, InvalidAttributeException, NullTagException, NullContentException, InvalidContentException {

        NodeList nl = document.getElementsByTagName("table");
        NodeList nl1;
        NodeList nl2;
        NodeList nl3;

        Table table = new Table();


        // <table>
        for (int i = 0; i < nl.getLength(); i++) {
//            System.out.println("i: " + i);
//            System.out.println(nl.item(i).getNodeName());

            Node tableName = nl.item(0).getAttributes().getNamedItem("name");
            Node tableSchema = nl.item(0).getAttributes().getNamedItem("schema");

            // Validates table
            if (nl.item(0) == null) {
                throw new NullTagException("table");
            }

            // Validates table name
            if (tableName == null) {
                throw new NullAttributeException("name");
            } else if (tableName.getTextContent().equals("")) {
                throw new InvalidAttributeException("name", "table");
            }

            // Validates table schema
            if (tableSchema == null) {
                throw new NullAttributeException("schema");
            } else if (tableSchema.getTextContent().equals("")) {
                throw new InvalidAttributeException("schema", "table");
            }

            table.setName(tableName.getTextContent().trim());
            table.setSchema(tableSchema.getTextContent().trim());

            nl1 = getNodes("table/*", document);

            int createCount = 0;
            int alterCount = 0;

            for (int j = 0; j < nl1.getLength(); j++) {
//                System.out.println("j: " + j);
//                System.out.println(nl1.item(j).getNodeName());

                /**************
                 *DDL Commands*
                 **************/

                /* <create> */
                if (nl1.item(j).getNodeName().equals("create") && createCount < 1) {
                    createCount++;

                    List<Column> columns = new ArrayList<Column>();
                    nl2 = getNodes("table/create[1]/*", document);

                    for (int k = 0; k < nl2.getLength(); k++) {
//                        System.out.println("k: " + k);
                        Node node = nl2.item(k);
//                        System.out.println(attributes.getNamedItem("type"));
                        if (node.getNodeName().equals("column")) {
                            NamedNodeMap attributes = node.getAttributes();
                            columns.add(CreateColumn(node, attributes));
                        };
                    }

                    table.setCreate(new Create(columns));
                } else if (nl1.item(j).getNodeName().equals("create") && createCount >= 1) {
                    throw new MultipleTagsException("create");
                }

                /* <alter> */
                if (nl1.item(j).getNodeName().equals("alter")) {
                    alterCount++;

                    int addCount = 0;
                    int dropColumnCount = 0;
                    int typeCount = 0;
                    int constraintCount = 0;

                    Alter alter = new Alter();
                    Add add = null;
                    List<Column> addColumns = new ArrayList<Column>();
                    DropColumn drop = null;
                    Type type = null;
                    Constraint constraint = null;
                    nl2 = getNodes("table/alter[" + alterCount + "]/*", document);

                    for (int k = 0; k < nl2.getLength(); k++) {

                        /* <add> */
                        if (nl2.item(k).getNodeName().equals("add") && addCount < 1) {
                            addCount++;
                            nl3 = getNodes("table/alter[" + alterCount + "]/add[1]/*", document);

                            for (int l = 0; l < nl3.getLength(); l++) {
//                        System.out.println("l: " + l);
                                Node node = nl3.item(l);
                                if (node.getNodeName().equals("column")) {
                                    NamedNodeMap attributes = node.getAttributes();
                                    addColumns.add(CreateColumn(node, attributes));
                                };
                            }

                            add = new Add(addColumns);

                        } else if (nl2.item(k).getNodeName().equals("add") && addCount >= 1) {
                            throw new MultipleTagsException("add");
                        }

                        /* <drop> */
                        if (nl2.item(k).getNodeName().equals("drop") && dropColumnCount < 1) {
                            dropColumnCount++;

                            String columnName;
                            Node node = nl2.item(k);
                            NamedNodeMap attributes = node.getAttributes();

                            try {
                                drop = new DropColumn(columnName = attributes.getNamedItem("column-name").getTextContent());
                            } catch (NullPointerException e) {
                                throw new NullAttributeException("column-name");
                            }

                            if (columnName.equals("")) {
                                throw new InvalidAttributeException("column-name", "drop");
                            }

                        } else if (nl2.item(k).getNodeName().equals("drop") && dropColumnCount >= 1) {
                            throw new MultipleTagsException("drop");
                        }

                        /* <type> */
                        if (nl2.item(k).getNodeName().equals("type") && typeCount < 1) {
                            typeCount++;

                            String columnName;
                            String typeString;
                            Node node = nl2.item(k);
                            NamedNodeMap attributes = node.getAttributes();

                            try {
                                columnName = (attributes.getNamedItem("column-name").getTextContent());
                            } catch (NullPointerException e) {
                                throw new NullAttributeException("column-name");
                            }

                            if (columnName.equals("")) {
                                throw new InvalidAttributeException("column-name", "type");
                            }

                            try {
                                typeString = (node.getTextContent().trim());
                                if (!validColumnTypes.contains(typeString)) {
                                    throw new InvalidContentException();
                                }

                            } catch (NullPointerException e) {
                                throw new NullContentException();
                            }

                            type = new Type(columnName, typeString);

                        } else if (nl2.item(k).getNodeName().equals("type") && typeCount >= 1) {
                            throw new MultipleTagsException("type");
                        }

                        /* <constraint> */
                        if (nl2.item(k).getNodeName().equals("constraint") && constraintCount < 1) {
                            constraintCount++;

                            String columnName;
                            String constraintString;
                            Node node = nl2.item(k);
                            NamedNodeMap attributes = node.getAttributes();

                            try {
                                columnName = (attributes.getNamedItem("column-name").getTextContent());
                            } catch (NullPointerException e) {
                                throw new NullAttributeException("column-name");
                            }

                            if (columnName.equals("")) {
                                throw new InvalidAttributeException("column-name", "constraint");
                            }

                            try {
                                constraintString = (node.getTextContent().trim());
                                if (!validConstraints.contains(constraintString)) {
                                    throw new InvalidContentException();
                                }

                            } catch (NullPointerException e) {
                                throw new NullAttributeException("constraint");
                            }

                            constraint = new Constraint(constraintString);

                        } else if (nl2.item(k).getNodeName().equals("constraint") && constraintCount >= 1) {
                            throw new MultipleTagsException("constraint");
                        }

                        alter.setAdd(add);
                        alter.setDropColumn(drop);
                        alter.setType(type);
                        alter.setConstraint(constraint);
                        table.addAlter(alter);

                    }

                    addCount = 0;
                    dropColumnCount = 0;
                    typeCount = 0;
                    constraintCount = 0;
                }

                /* <drop> */
                if (nl1.item(j).getNodeName().equals("drop")) {
                    table.setDrop(new Drop());
                }


                /**************
                 *DML Commands*
                 **************/


                // TODO: DML
                /* <insert */
                if (nl1.item(j).getNodeName().equals("insert")) {
                    table.setInsert(null);
                }

                /* export */
                if (nl1.item(j).getNodeName().equals("export")) {
                    table.setDrop(null);
                }

                /* update */
                if (nl1.item(j).getNodeName().equals("update")) {
                    table.setDrop(null);
                }

                /* delete */
                if (nl1.item(j).getNodeName().equals("delete")) {
                    table.setDrop(null);
                }

            }

        }

        return table;
    }

    public Column CreateColumn(Node node, NamedNodeMap attributes) throws NullAttributeException, NullContentException, NullTagException, InvalidAttributeException {
        if (node.getTextContent().equals("")) {
            throw new NullContentException();
        }

        if (!validColumnTypes.contains(attributes.getNamedItem("type").getTextContent())) {
            throw new InvalidAttributeException("type", "column");
        }

        try {
            Column column = new Column(
                node.getTextContent().trim(),
                attributes.getNamedItem("type").getTextContent(),
                attributes.getNamedItem("not-empty") != null && attributes.getNamedItem("not-empty").getTextContent().equals("true"),
                attributes.getNamedItem("unique") != null && attributes.getNamedItem("unique").getTextContent().equals("true"),
                attributes.getNamedItem("unique-id") != null && attributes.getNamedItem("unique-id").getTextContent().equals("true")
            );

            return column;
        } catch (NullPointerException e) {
            throw new NullTagException("type");
        }
    }
}
