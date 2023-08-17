package learn.services;

import dorkbox.annotation.AnnotationDefaults;
import dorkbox.annotation.AnnotationDetector;
import learn.annotations.DbEntity;

import java.lang.annotation.ElementType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityServices {
    private String topLevelPackage;
    private Map<String, EntityMetadata> metadataMap;

    public EntityServices(String topLevelPackage) {
        this.topLevelPackage = topLevelPackage;

        metadataMap = new HashMap<>();

        try {
            List<Class<?>> entities = AnnotationDetector
                    .scanClassPath(topLevelPackage)
                    .forAnnotations(DbEntity.class)
                    .on(ElementType.TYPE)
                    .collect(AnnotationDefaults.getType);

            for (Class<?> clazz : entities) {
                metadataMap.put(clazz.getName(), new EntityMetadata(clazz));
            }
        } catch (Exception e) {
            System.out.printf("Error %s\n", e.getMessage());
        }
    }

    public void debugInsertStatement(Object entity) {
        if (metadataMap.containsKey(entity.getClass().getName())) {
            EntityMetadata metadata = metadataMap.get(entity.getClass().getName());
            String insertStatement = metadata.entityInsertStatement(entity);
            System.out.println(insertStatement);
        } else {
            System.out.println("Not Entity!");
        }
    }

    public void debug() {
        for (Map.Entry<String, EntityMetadata> keyMetadata : metadataMap.entrySet()) {
            System.out.printf("Entity: %s\n", keyMetadata.getKey());
            EntityMetadata metadata = keyMetadata.getValue();
            metadata.debug();
        }
    }
}
