package org.database;

import java.util.List;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.util.Tuple;

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

    // region getters
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
        String query = "SELECT * FROM " + attributeTable + " WHERE entity_type_id = " + entity.getEntityTypeId();
        return conn.createQuery(query).executeAndFetch(EavAttribute.class);
    }

    public List<EavValue> getValues(EavEntity entity) {
        String query = "SELECT * FROM " + valueTable + " WHERE entity_id = " + entity.getId();
        return conn.createQuery(query).executeAndFetch(EavValue.class);
    }

    public List<EavView> getEverything() {
        String query = "SELECT * FROM all_existing_eav_data";
        return conn.createQuery(query).executeAndFetch(EavView.class);
    }
    // endregion getters

    // region setters
    public Tuple<EavEntityType, EavEntity> createEntity(String entity_type, String entity) {
        if (entity.isEmpty() || entity_type.isEmpty()) {
            throw new IllegalArgumentException("Err: parameters cannot be empty");
        }

        String query = "CALL create_eav_entity(:entity_type, :entity);";
        conn.createQuery(query)
                .addParameter("entity_type", entity_type)
                .addParameter("entity", entity)
                .executeUpdate();

        // fetch new resources
        String query_a = "SELECT * FROM " + entityTypeTable + " WHERE entity_type = :entity_type";
        List<EavEntityType> list_a = conn.createQuery(query_a)
                .addParameter("entity_type", entity_type)
                .executeAndFetch(EavEntityType.class);
        if (list_a.isEmpty()) {
            throw new RuntimeException("Err: Failed to create entity_type");
        }

        String query_b = "SELECT * FROM " + entityTable + " WHERE entity = :entity AND entity_type_id = :entity_type_id";
        List<EavEntity> list_b = conn.createQuery(query_b)
                .addParameter("entity", entity)
                .addParameter("entity_type_id", list_a.get(0).getId())
                .executeAndFetch(EavEntity.class);
        if (list_b.isEmpty()) {
            throw new RuntimeException("Err: Failed to create entity");
        }

        return new Tuple<>(list_a.get(0), list_b.get(0));
    }

    public EavAttribute createAttribute(EavEntity entity, EavAttribute attribute) {
        // todo: call create_eav_attr(:entity_id, :attr)
        return attribute;
    }
    // endregion setters
}
