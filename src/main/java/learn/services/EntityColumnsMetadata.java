package learn.services;

import learn.annotations.DbColumn;

import java.lang.reflect.Field;

public class EntityColumnsMetadata {
    private Field field;
    private String columnName;

    public EntityColumnsMetadata(Field field) {
        this.field = field;

        DbColumn dbColumn = field.getAnnotation(DbColumn.class);
        columnName = dbColumn.name();
    }

    public String getColumnName() {
        return columnName;
    }

    public void debug() {
        System.out.printf("Column Name: %s\n", columnName);
        System.out.printf("Field Type Object: %s\n", field.getType().getName());
    }
}
