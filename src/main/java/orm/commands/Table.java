package orm.commands;

import orm.commands.table.*;

import java.util.ArrayList;
import java.util.List;

public class Table {
    String schema;
    String name;

    Create create;
    List<Alter> alter;
    Drop drop;

    List<Insert> insert;
    List<Export> export;
    List<Update> update;
    List<Delete> delete;

    public Table() {
        alter = new ArrayList<>();
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /* DDL */
    public Create getCreate() {
        return create;
    }

    public void setCreate(Create create) {
        this.create = create;
    }

    public List<Alter> getAlter() {
        return alter;
    }

    public void addAlter(Alter alter) {
        this.alter.add(alter);
    }

    public Drop getDrop() {
        return drop;
    }

    public void setDrop(Drop drop) {
        this.drop = drop;
    }

    /* DML */
    public List<Insert> getInsert() {
        return insert;
    }

    public void addInsert(Insert insert) {
        this.insert.add(insert);
    }

    public List<Export> getExport() {
        return export;
    }

    public void addExport(Export export) {
        this.export.add(export);
    }

    public List<Update> getUpdate() {
        return update;
    }

    public void addUpdate(Update update) {
        this.update.add(update);
    }

    public List<Delete> getDelete() {
        return delete;
    }

    public void addDelete(Delete delete) {
        this.delete.add(delete);
    }
}
