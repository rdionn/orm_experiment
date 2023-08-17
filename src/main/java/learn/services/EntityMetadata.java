package learn.services;

import learn.annotations.DbColumn;
import learn.annotations.DbTable;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityMetadata {
    private Class<?> entityClass;
    private String tableName;
    private Map<String, EntityColumnsMetadata> columnsMetadataMap;

    public EntityMetadata(Class<?> entityClass) {
        this.entityClass = entityClass;

        DbTable tableAnnotation = entityClass.getAnnotation(DbTable.class);
        if (tableAnnotation != null) {
            tableName = tableAnnotation.name();
        } else {
            tableName = entityClass.getName();
        }

        processFields();
    }

    public void debug() {
        System.out.printf("Table Name: %s\n", tableName);
        for (Map.Entry<String, EntityColumnsMetadata> keyMetadata : columnsMetadataMap.entrySet()) {
            System.out.printf("Field: %s\n", keyMetadata.getKey());
            EntityColumnsMetadata columnsMetadata = keyMetadata.getValue();
            columnsMetadata.debug();
        }
    }

    public String entityInsertStatement(Object entity) {
        List<String> columns = columnsMetadataMap.values().stream().map(EntityColumnsMetadata::getColumnName).toList();
        List<String> fieldsValues = new ArrayList<>();

        try {
            for (Field field : entityClass.getDeclaredFields()) {
                field.setAccessible(true);

                if (field.getType().equals(String.class)) {
                    fieldsValues.add(String.format("'%s'", field.get(entity)));
                } else {
                    throw new RuntimeException("Unknown how to convert type to sql");
                }
            }
        } catch (Exception e) {
            System.out.printf("Error %s\n", e.getMessage());
        }

        return String.format("Insert Into %s(%s) Values (%s)", tableName, String.join(",", columns), String.join(",", fieldsValues));
    }

    private void processFields() {
        columnsMetadataMap = new HashMap<>();

        for (Field field : entityClass.getDeclaredFields()) {
            DbColumn column = field.getAnnotation(DbColumn.class);
            if (column != null) {
                columnsMetadataMap.put(field.getName(), new EntityColumnsMetadata(field));
            }
        }
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
