package orm;

import java.sql.SQLException;
import java.util.List;

public interface DbContext<E> {

    void persist(E entity) throws IllegalAccessException, SQLException;

    List<E> find(Class<E> table , String where, Object... values);

    E findFirst(Class<E> table, String where, Object... values);

    E findById(Class<E> table, int id);

    int delete(Class<E> table, int id);

}
