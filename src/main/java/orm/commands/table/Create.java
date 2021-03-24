package orm.commands.table;

import orm.commands.Column;

import java.util.List;

public class Create implements Comparable<Create>  {
    List<Column> columns;

    public Create(List<Column> columns) {
        this.columns = columns;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public int compareTo(Create c) {
        assert columns.size() == c.columns.size();
        for (int j = 0; j < columns.size(); j++) {
            int i = columns.get(j).compareTo(c.getColumns().get(j));
            if (i != 0) return i;
        }

        return 0;
    }
}
