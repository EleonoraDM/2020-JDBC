package orm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface DbContext<E> {

    void persist(E entity) throws IllegalAccessException, SQLException;

    List<E> find(Class<E> table , String whereClause, Object... values);

    String findFirst(Class<E> table, String whereClause, Object... values) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    String findById(Class<E> table, int id) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, SQLException;

    void delete(Class<E> table, int id) throws SQLException;

}
