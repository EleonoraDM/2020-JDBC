package orm;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

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
    public boolean persist(E entity) throws IllegalAccessException, SQLException {
        Field primary = this.getId(entity.getClass());
        primary.setAccessible(true);
        Object idValue = primary.get(entity);

        return (idValue == null || ((int) idValue) <= 0)
                ? this.doInsert(entity, primary) :
                this.doUpdate(entity, primary);
    }

    private boolean doUpdate(E entity, Field primary) throws IllegalAccessException, SQLException {
        String tableName = this.getTableName(entity.getClass());

        Map<String, String> fields = collectFieldsWithValues(entity);

        List<String> joinedFields = fieldValuesToString(fields);

        String updateQuery = String.format(UPDATE_QUERY,
                tableName,
                String.join(", ", joinedFields),
                "id = " + primary.get(entity));

        return executeQuery(updateQuery);
    }

    private List<String> fieldValuesToString(Map<String, String> fields) {
        StringBuilder builder;
        List<String> joinedNamesAndValues = new ArrayList<>();

        for (Map.Entry<String, String> entry : fields.entrySet()) {
            builder = new StringBuilder();
            builder
                    .append(entry.getKey())
                    .append("=")
                    .append(entry.getValue());
            joinedNamesAndValues.add(builder.toString());
        }
        return joinedNamesAndValues;
    }

    private boolean doInsert(E entity, Field primary) throws SQLException {
        String tableName = this.getTableName(entity.getClass());

        Map<String, String> fields = collectFieldsWithValues(entity);

        String insertQuery = String.format
                (INSERT_QUERY,
                        tableName,
                        String.join(", ", fields.keySet()),
                        String.join(", ", fields.values()));

        return executeQuery(insertQuery);
    }

    private boolean executeQuery(String insertQuery) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(insertQuery);
        return stmt.execute();
    }

    private Map<String, String> collectFieldsWithValues(E entity) {
        Map<String, String> fieldsWithValues = new LinkedHashMap<>();

        Arrays.stream(entity.getClass().getDeclaredFields()).forEach(field -> {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                getFieldValue(entity, fieldsWithValues, field);
            }
        });
        return fieldsWithValues;
    }

    private void getFieldValue(E entity, Map<String, String> fieldsWithValues, Field field) {
        try {
            Object value = field.get(entity);
            String fieldValue =
                    field.getType() == String.class || field.getType() == LocalDate.class
                            ? String.format("'%s'", value.toString())
                            : value.toString();
            fieldsWithValues.put(field.getAnnotation(Column.class).name(), fieldValue);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Field getId(Class entity) {
        Field[] fields = entity.getDeclaredFields();
        Field idField = Arrays.stream(fields).
                filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst().
                        orElseThrow(() -> new UnsupportedOperationException("Entity does not have primary key!"));

        return idField;
    }

    private String getTableName(Class<?> entity) {
        return entity.isAnnotationPresent(Entity.class)
                ? entity.getAnnotation(Entity.class).name()
                : entity.getSimpleName();
    }

    @Override
    public Iterable<E> find(Class<E> table) {
        //TODO Implementation
        return null;
    }

    @Override
    public Iterable<E> find(Class<E> table, String where) {
        //TODO Implementation
        return null;
    }

    @Override
    public E findFirst(Class<E> table) {
        //TODO Implementation
        return null;
    }

    @Override
    public E findFirst(Class<E> table, String where) {
        //TODO Implementation
        return null;
    }


}
