package orm.commands;

import org.jetbrains.annotations.NotNull;
import orm.commands.table.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Table objects are created by XmlToJava and converted by JavaToSql
 */
public class Table implements Comparable<Table> {
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
        insert = new ArrayList<>();
        export = new ArrayList<>();
        update = new ArrayList<>();
        delete = new ArrayList<>();
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

    @Override
    public int compareTo(@NotNull Table t) {
        int i = name.compareTo(t.name);
        if (i != 0) return i;

        i = schema.compareTo(t.schema);
        if (i != 0) return i;

        try {
            i = create.compareTo(t.getCreate());
            if (i != 0) return i;

            i = drop.compareTo(t.getDrop());
            if (i != 0) return i;
        } catch (NullPointerException e) {
            // Continues if not found
        }

        assert alter.size() == t.getAlter().size();
        for (int j = 0; j < alter.size(); j++) {
            i = alter.get(j).compareTo(t.getAlter().get(j));
            if (i != 0) return i;
        }

        assert insert.size() == t.getInsert().size();
        for (int j = 0; j < insert.size(); j++) {
            i = insert.get(j).compareTo(t.getInsert().get(j));
            if (i != 0) return i;
        }

        assert export.size() == t.getExport().size();
        for (int j = 0; j < export.size(); j++) {
            i = export.get(j).compareTo(t.getExport().get(j));
            if (i != 0) return i;
        }

        assert update.size() == t.getUpdate().size();
        for (int j = 0; j < update.size(); j++) {
            i = update.get(j).compareTo(t.getUpdate().get(j));
            if (i != 0) return i;
        }

        assert delete.size() == t.getDelete().size();
        for (int j = 0; j < delete.size(); j++) {
            i = delete.get(j).compareTo(t.getDelete().get(j));
            if (i != 0) return i;
        }
        return 0;
    }
}
