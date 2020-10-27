package orm;

import entities.User;
import orm.annotations.Entity;

import javax.swing.plaf.synth.SynthRadioButtonMenuItemUI;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
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
    public List<E> find(Class<E> table, String whereClause, Object... values) {
        //TODO Implementation!!!
        return null;
    }

    @Override
    public String findFirst(Class<E> table, String whereClause, Object... values) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String tableName = EntityManagerUtils.getTableName(table);
        String result = null;

        if (table.isAnnotationPresent(Entity.class)) {

            String query =
                    SELECT_STAR_FROM + tableName +
                            " WHERE " + whereClause + " LIMIT 1;";
            PreparedStatement stmt = connection.prepareStatement(query);
            setInputParameter(stmt, values);
            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();

            result = fillEntity(table, resultSet);
        }
        return result;
    }

    private void setInputParameter(PreparedStatement stmt, Object[] values) throws SQLException {
        for (Object value : values) {
            switch (value.getClass().getSimpleName()) {
                case "Integer":
                    stmt.setInt(1,Integer.parseInt(value.toString()));
                    break;
                case "Double":
                    stmt.setDouble(1,Double.parseDouble(value.toString()));
                    break;
                default: stmt.setString(1, value.toString());
            }
        }
    }

    @Override
    public String findById(Class<E> table, int id) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, SQLException {
        System.out.printf("User with id=%d: ", id);
        return findFirst(table, "id = ?", id);
    }

    private String fillEntity(Class<E> table, ResultSet resultSet) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SQLException {
        String result;
        E entity = table.getDeclaredConstructor
                (String.class, String.class, int.class, Date.class)
                .newInstance(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getInt("age"),
                        resultSet.getDate("registration_date"));
        result = entity.toString();
        return result;
    }

    @Override
    public void delete(Class<E> table, int id) throws SQLException {
        String tableName = EntityManagerUtils.getTableName(table);
        String query = String.format(DELETE_QUERY, tableName, "id =" + id);

        int rows = EntityManagerUtils.executeUpdate(connection, query);

        if (rows > 0) {
            System.out.printf("%d row was deleted for entity with id %d!", rows, id);
        }
    }

    private void doUpdate(Object entity, Field primary) throws IllegalAccessException, SQLException {
        String tableName = EntityManagerUtils.getTableName(entity.getClass());

        Map<String, String> fields = EntityManagerUtils.collectFieldsWithValues(entity);

        List<String> joinedFields = EntityManagerUtils.fieldValuesToString(fields);

        String updateQuery = String.format(UPDATE_QUERY,
                tableName,
                String.join(", ", joinedFields),
                "id = " + primary.get(entity));
        EntityManagerUtils.executeUpdate(connection, updateQuery);

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
        EntityManagerUtils.executeUpdate(connection, insertQuery);

        System.out.println("1 row was inserted!");
    }
}
