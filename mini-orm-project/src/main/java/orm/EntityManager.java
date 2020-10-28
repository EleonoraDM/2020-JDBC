package orm;

import orm.annotations.Entity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        Field primary = EntityManagerUtils.getIdField(entity.getClass());
        primary.setAccessible(true);
        Object idValue = primary.get(entity);

        if ((idValue == null || ((int) idValue) <= 0)) {
            this.doInsert(entity);
        } else {
            this.doUpdate(entity, primary);
        }
    }

    @Override
    public List<E> find(Class<E> table, String whereClause, Object... values) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        String tableName = EntityManagerUtils.getTableName(table);
        List<E> allMatches = new ArrayList<>();
        E entity;

        if (table.isAnnotationPresent(Entity.class)) {
            String query =
                    SELECT_STAR_FROM + tableName +
                            " WHERE " + whereClause.substring(0, whereClause.indexOf("?") + 1)
                            + " AND " + whereClause.substring(whereClause.indexOf("?") + 1);
            PreparedStatement stmt = connection.prepareStatement(query);

            EntityManagerUtils.setInputParameter(stmt, values);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                entity = fillEntity(table, resultSet);
                allMatches.add(entity);
            }
        }
        return allMatches;
    }

    @Override
    public E findFirst(Class<E> table, String whereClause, Object... values) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String tableName = EntityManagerUtils.getTableName(table);

        E entity = null;

        if (table.isAnnotationPresent(Entity.class)) {

            String query =
                    SELECT_STAR_FROM + tableName +
                            " WHERE " + whereClause + " LIMIT 1;";
            PreparedStatement stmt = connection.prepareStatement(query);
            EntityManagerUtils.setInputParameter(stmt, values);
            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();

            entity = fillEntity(table, resultSet);
        }
        return entity;
    }


    @Override
    public E findById(Class<E> table, int id) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, SQLException {
        return findFirst(table, "id = ?", id);
    }

    @Override
    public void delete(Class<E> table, int id) throws SQLException {
        String tableName = EntityManagerUtils.getTableName(table);
        String query = String.format(DELETE_QUERY, tableName, "id =" + id);

        int rows = EntityManagerUtils.executeUpdate(connection, query);

        if (rows > 0) {
            System.out.printf("%d row was deleted for entity with id %d%n!", rows, id);
        }else {
            System.out.printf("User with id %d does not exist at the DB!%n", id);
        }
    }

    @Override
    public void printObjectData(E entity) {
        System.out.println(entity.toString());
    }

    @Override
    public void printMultipleObjectsData(List<E> objects) {
        objects.stream().map(Objects::toString).forEach(System.out::println);
    }

    private E fillEntity(Class<E> table, ResultSet resultSet) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SQLException {

        E entity = table.getDeclaredConstructor
                (String.class, String.class, int.class, LocalDate.class)
                .newInstance(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getInt("age"),
                        resultSet.getDate("registration_date").toLocalDate());

        Field idField = EntityManagerUtils.getIdField(table);
        idField.setAccessible(true);
        idField.set(entity, resultSet.getInt("id"));

        return entity;
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

    private void doInsert(Object entity) throws SQLException {
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
