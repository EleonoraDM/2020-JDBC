package orm;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Id;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

class EntityManagerUtils {


    static void setInputParameter(PreparedStatement stmt, Object[] values) throws SQLException {
        for (int i = 0; i < values.length; i++) {

            switch (values[i].getClass().getSimpleName()) {
                case "Integer":
                    stmt.setInt(i+1, Integer.parseInt(values[i].toString()));
                    break;
                case "Double":
                    stmt.setDouble(i+1, Double.parseDouble(values[i].toString()));
                    break;
                default:
                    stmt.setString(i+1, values[i].toString());
            }
        }
    }

    static List<String> fieldValuesToString(Map<String, String> fields) {
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

    static int executeUpdate(Connection conn, String query) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(query);
        return stmt.executeUpdate();
    }

    static Map<String, String> collectFieldsWithValues(Object entity) {
        Map<String, String> fieldsWithValues = new LinkedHashMap<>();

        Arrays.stream(entity.getClass().getDeclaredFields()).forEach(field -> {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                getFieldValue(entity, fieldsWithValues, field);
            }
        });
        return fieldsWithValues;
    }

    private static void getFieldValue(Object entity, Map<String, String> fieldsWithValues, Field field) {
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

    static Field getIdField(Class entity) {
        Field[] fields = entity.getDeclaredFields();
        Field idField = Arrays.stream(fields).
                filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst().
                        orElseThrow(() -> new UnsupportedOperationException("Entity does not have primary key!"));

        return idField;
    }

    static String getTableName(Class<?> entity) {
        return entity.isAnnotationPresent(Entity.class)
                ? entity.getAnnotation(Entity.class).name()
                : entity.getSimpleName();
    }

}
