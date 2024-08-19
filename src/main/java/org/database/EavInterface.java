package org.database;

import java.util.List;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class EavInterface {
    private final Connection conn;

    public final String entityTypeTable = "eav_entity_types";
    public final String entityTable = "eav_entities";
    public final String attributeTable = "eav_attrs";
    public final String valueTable = "eav_values";

    // constructor
    public EavInterface(String server, String dbName) {
        if (server.isEmpty() || dbName.isEmpty()) {
            throw new IllegalArgumentException("Required arguments not provided");
        }
        Sql2o db = new Sql2o(
                "jdbc:mysql://" + server + "/" + dbName + "?allowPublicKeyRetrieval=true",
                "root", "password"
        );
        try (Connection c = db.open()) {
            conn = c;
        }
    }

    // getters
    public <T> List<T> get(Class<T> schema, String target) {
        if (target.isEmpty()) {
            throw new IllegalArgumentException("Query target not provided");
        }
        String query = "SELECT * FROM " + target;
        return conn.createQuery(query).executeAndFetch(schema);
    }

    public List<EavEntityType> getEntityTypes() {
        String query = "SELECT * FROM " + entityTypeTable;
        return conn.createQuery(query).executeAndFetch(EavEntityType.class);
    }

    public List<EavEntity> getEntities() {
        String query = "SELECT * FROM " + entityTable;
        return conn.createQuery(query).executeAndFetch(EavEntity.class);
    }

    public List<EavEntity> getEntities(EavEntityType entityType) {
        String query = "SELECT * FROM " + entityTable + " WHERE entity_type_id = " + entityType.getId();
        return conn.createQuery(query).executeAndFetch(EavEntity.class);
    }

    public List<EavAttribute> getAttributes(EavEntityType entityType) {
        String query = "SELECT * FROM " + attributeTable + " WHERE entity_type_id = " + entityType.getId();
        return conn.createQuery(query).executeAndFetch(EavAttribute.class);
    }

    public List<EavAttribute> getAttributes(EavEntity entity) {
        String query = "SELECT * FROM " + attributeTable + " WHERE entity_type_id = " + entity.getEntity_type_id();
        return conn.createQuery(query).executeAndFetch(EavAttribute.class);
    }

    public List<EavValue> getValues(EavEntity entity) {
        String query = "SELECT * FROM " + valueTable + " WHERE entity_id = " + entity.getId();
        return conn.createQuery(query).executeAndFetch(EavValue.class);
    }

    // setters
}
