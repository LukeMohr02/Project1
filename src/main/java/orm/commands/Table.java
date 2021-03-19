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

    Insert insert;
    Export export;
    Update update;
    Delete delete;

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

    public Insert getInsert() {
        return insert;
    }

    public void setInsert(Insert insert) {
        this.insert = insert;
    }

    public Export getExport() {
        return export;
    }

    public void setExport(Export export) {
        this.export = export;
    }

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public Delete getDelete() {
        return delete;
    }

    public void setDelete(Delete delete) {
        this.delete = delete;
    }
}
