package orm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface DbContext<E> {

    void persist(E entity) throws IllegalAccessException, SQLException;

    List<E> find(Class<E> table, String whereClause, Object... values) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException;

    E findFirst(Class<E> table, String whereClause, Object... values) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    E findById(Class<E> table, int id) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, SQLException;

    void delete(Class<E> table, int id) throws SQLException;

    void printObjectData(E entity);

    void printMultipleObjectsData(List<E> objects);

}
