package orm;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class EntityManager<E> implements DbContext<E> {
    private static final String INSERT_QUERY = "INSERT INTO %s (%s) VALUE (%s);";
    private static final String UPDATE_QUERY = "UPDATE %s SET %s WHERE %s;";
    private static final String DELETE_QUERY = "DELETE FROM %s WHERE %s;";
    private static final String SELECT_STAR_FROM = "SELECT * FROM   ";

    private Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void persist(E entity) throws IllegalAccessException, SQLException {
        Field primary = EntityManagerUtils.getId(entity.getClass());
        primary.setAccessible(true);
        Object idValue = primary.get(entity);

        if ((idValue == null || ((int) idValue) <= 0)) {
            this.doInsert(entity, primary);
        } else {
            this.doUpdate(entity, primary);
        }
    }

    @Override
    public List<E> find(Class<E> table, String where, Object... values) {
        return null;
    }

    @Override
    public E findFirst(Class<E> table, String where, Object... values) {
        return null;
    }

    @Override
    public E findById(Class<E> table, int id) {
        return null;
    }

    @Override
    public int delete(Class<E> table, int id) {
        return 0;
    }

    private void doUpdate(Object entity, Field primary) throws IllegalAccessException, SQLException {
        String tableName = EntityManagerUtils.getTableName(entity.getClass());

        Map<String, String> fields = EntityManagerUtils.collectFieldsWithValues(entity);

        List<String> joinedFields = EntityManagerUtils.fieldValuesToString(fields);

        String updateQuery = String.format(UPDATE_QUERY,
                tableName,
                String.join(", ", joinedFields),
                "id = " + primary.get(entity));
        EntityManagerUtils.executeUpdate(connection,updateQuery);

        System.out.println("1 row was updated!");
    }

    private void doInsert(Object entity, Field primary) throws SQLException {
        String tableName = EntityManagerUtils.getTableName(entity.getClass());

        Map<String, String> fields = EntityManagerUtils.collectFieldsWithValues(entity);

        String insertQuery = String.format
                (INSERT_QUERY,
                        tableName,
                        String.join(", ", fields.keySet()),
                        String.join(", ", fields.values()));
        EntityManagerUtils.executeUpdate(connection,insertQuery);

        System.out.println("1 row was inserted!");
    }
}
