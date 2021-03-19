package orm.dao;

//<T> --> class used for dao object
//<K> --> primary key used by class
public interface GenericDAO<T, K> {

    void insert(T t);

    T get(K id);

    T[] getAll();

    void update(K id, String columnName, String value);

    void delete(K id);

    void callFunction();

    //void updateAll(collection c);
}
