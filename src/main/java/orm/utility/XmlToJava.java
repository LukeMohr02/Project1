package orm.utility;

import org.w3c.dom.*;
import orm.commands.*;
import orm.commands.table.*;
import orm.commands.table.alter.*;
import orm.commands.table.condition.Condition;
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
    List<String> validOperators = new ArrayList<>(Arrays.asList("=","!=",">","less",">=","less="));

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
    public Table runDDL(Document document) throws XPathExpressionException, MultipleTagsException, NullAttributeException, InvalidAttributeException, NullTagException, NullContentException, InvalidContentException {

        NodeList nl = document.getElementsByTagName("table");
        NodeList nl1;
        NodeList nl2;
        NodeList nl3;

        Table table = new Table();


        // <table>
        for (int i = 0; i < nl.getLength(); i++) {
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


                /**************
                 *DDL Commands*
                 **************/


                /* <create> */
                if (nl1.item(j).getNodeName().equals("create") && createCount < 1) {
                    createCount++;

                    List<Column> columns = new ArrayList<>();
                    nl2 = getNodes("table/create[1]/*", document);

                    for (int k = 0; k < nl2.getLength(); k++) {
                        Node node = nl2.item(k);
                        if (node.getNodeName().equals("column")) {
                            NamedNodeMap attributes = node.getAttributes();
                            columns.add(CreateColumn(node, attributes));
                        }
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
                    List<Column> addColumns = new ArrayList<>();
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
                                Node node = nl3.item(l);
                                if (node.getNodeName().equals("column")) {
                                    NamedNodeMap attributes = node.getAttributes();
                                    addColumns.add(CreateColumn(node, attributes));
                                }
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
                                drop = new DropColumn(columnName = attributes.getNamedItem("column").getTextContent());
                            } catch (NullPointerException e) {
                                throw new NullAttributeException("column");
                            }

                            if (columnName.equals("")) {
                                throw new InvalidAttributeException("column", "drop");
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
                                columnName = (attributes.getNamedItem("column").getTextContent());
                            } catch (NullPointerException e) {
                                throw new NullAttributeException("column");
                            }

                            if (columnName.equals("")) {
                                throw new InvalidAttributeException("column", "type");
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
                                columnName = (attributes.getNamedItem("column").getTextContent());
                            } catch (NullPointerException e) {
                                throw new NullAttributeException("column");
                            }

                            if (columnName.equals("")) {
                                throw new InvalidAttributeException("column", "constraint");
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

            }

        }

        return table;
    }

    public Table runDML(Document document) throws XPathExpressionException, MultipleTagsException, NullAttributeException, InvalidAttributeException, NullTagException, NullContentException, InvalidContentException, ColumnMismatchException {
        NodeList nl = document.getElementsByTagName("table");
        NodeList nl1;
        NodeList nl2;

        Table table = new Table();


        // <table>
        for (int i = 0; i < nl.getLength(); i++) {

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

            int exportCount = 0;
            int updateCount = 0;
            int deleteCount = 0;

            for (int j = 0; j < nl1.getLength(); j++) {


                /**************
                 *DML Commands*
                 **************/


                /* <insert> */
                if (nl1.item(j).getNodeName().equals("insert")) {
                    String columns;
                    String values;
                    Node node = nl1.item(j);
                    NamedNodeMap attributes = node.getAttributes();

                    columns = attributes.getNamedItem("columns").getTextContent();
                    values = node.getTextContent().trim();

                    table.addInsert(new Insert(columns, values));
                }

                /* <export> */
                if (nl1.item(j).getNodeName().equals("export")) {
                    exportCount++;
                    String columns = "";
                    List<Condition> conditions = new ArrayList<>();
                    Node node = nl1.item(j);
                    NamedNodeMap attributes = node.getAttributes();

                    try {
                        columns = attributes.getNamedItem("columns").getTextContent();
                    } catch (NullPointerException e) {
                        // columns attribute is optional
                    }

                    nl2 = getNodes("table/export[" + exportCount + "]/*", document);

                    for (int k = 0; k < nl2.getLength(); k++) {
                        Node node1 = nl2.item(k);
                        if (node1.getNodeName().equals("condition")) {
                            conditions.add(CreateCondition(node1, node1.getAttributes()));
                        }
                    }

                    table.addExport(new Export(columns, conditions));
                }

                /* <update> */
                if (nl1.item(j).getNodeName().equals("update")) {
                    updateCount++;
                    String columns;
                    String values = "";
                    List<Condition> conditions = new ArrayList<>();
                    Node node = nl1.item(j);
                    NamedNodeMap attributes = node.getAttributes();

                    try {
                        columns = attributes.getNamedItem("columns").getTextContent();
                    } catch (NullPointerException e) {
                        throw new NullAttributeException("columns");
                    }

                    nl2 = getNodes("table/update[" + updateCount + "]/*", document);

                    for (int k = 0; k < nl2.getLength(); k++) {
                        Node node1 = nl2.item(k);

                        if (node1.getNodeName().equals("values")) {
                            values = node1.getTextContent().trim();
                        }

                        if (node1.getNodeName().equals("condition")) {
                            conditions.add(CreateCondition(node1, node1.getAttributes()));
                        }

                    }

                    table.addUpdate(new Update(columns, values, conditions));
                }

                /* <delete> */
                if (nl1.item(j).getNodeName().equals("delete")) {
                    deleteCount++;
                    List<Condition> conditions = new ArrayList<>();

                    nl2 = getNodes("table/delete[" + deleteCount + "]/*", document);

                    for (int k = 0; k < nl2.getLength(); k++) {
                        Node node1 = nl2.item(k);

                        if (node1.getNodeName().equals("condition")) {
                            conditions.add(CreateCondition(node1, node1.getAttributes()));
                        }

                    }

                    table.addDelete(new Delete(conditions));
                }

            }

            exportCount = 0;
            updateCount = 0;
            deleteCount = 0;

        }

        return table;
    }

    public Column CreateColumn(Node node, NamedNodeMap attributes) throws NullAttributeException, NullContentException, NullTagException, InvalidAttributeException {
        if (node.getTextContent().trim().equals("")) {
            throw new NullContentException();
        }

        try {
            if (!validColumnTypes.contains(attributes.getNamedItem("type").getTextContent())) {
                throw new InvalidAttributeException("type", "column");
            }
        } catch (NullPointerException e) {
            throw new NullAttributeException("type");
        }

        try {

            return new Column(
                node.getTextContent().trim(),
                attributes.getNamedItem("type").getTextContent(),
                attributes.getNamedItem("not-empty") != null && attributes.getNamedItem("not-empty").getTextContent().equals("true"),
                attributes.getNamedItem("unique") != null && attributes.getNamedItem("unique").getTextContent().equals("true"),
                attributes.getNamedItem("unique-id") != null && attributes.getNamedItem("unique-id").getTextContent().equals("true")
            );
        } catch (NullPointerException e) {
            throw new NullTagException("type");
        }
    }

    public Condition CreateCondition(Node node, NamedNodeMap attributes) throws NullContentException, InvalidAttributeException, NullAttributeException {
        if (node.getTextContent().trim().equals("")) {
            throw new NullContentException();
        }

        try {
            if (!validOperators.contains(attributes.getNamedItem("operation").getTextContent())) {
                throw new InvalidAttributeException("operation", "condition");
            }
        } catch (NullPointerException e) {
            throw new NullAttributeException("operation");
        }

        try {
            return new Condition(
                    attributes.getNamedItem("column").getTextContent(),
                    attributes.getNamedItem("operation").getTextContent(),
                    node.getTextContent().trim()
            );
        } catch (NullPointerException e) {
            throw new NullAttributeException("column");
        }
    }
}
