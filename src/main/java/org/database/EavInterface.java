package org.database;

import java.util.Collection;
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

    public <T> List<T> get(Class<T> schema, String target) {
        if (target.isEmpty()) {
            throw new IllegalArgumentException("Query target not provided");
        }
        String query = "SELECT * FROM " + target;
        return conn.createQuery(query).executeAndFetch(schema);
    }

    // region entityType
    public List<EavEntityType> getEntityTypes() {
        String query = "SELECT * FROM " + entityTypeTable;
        return conn.createQuery(query).executeAndFetch(EavEntityType.class);
    }

    public boolean deleteEntityType(EavEntityType entityType) {
        String query1 = "DELETE FROM " + entityTypeTable + " WHERE id = :id";
        int results = conn.createQuery(query1)
                .addParameter("id", entityType.getId())
                .executeUpdate()
                .getResult();

        return results > 0;
    }
    // endregion entityType

    // region entity
    public List<EavEntity> getEntities() {
        String query = "SELECT * FROM " + entityTable;
        return conn.createQuery(query).executeAndFetch(EavEntity.class);
    }

    public List<EavEntity> getEntities(EavEntityType entityType) {
        String query = "SELECT * FROM " + entityTable + " WHERE entity_type_id = " + entityType.getId();
        return conn.createQuery(query).executeAndFetch(EavEntity.class);
    }

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

    public boolean deleteEntity(EavEntity entity) {
        // delete all values for entity
        String query1 = "DELETE FROM " + valueTable + " WHERE entity_id = :entity_id";
        int results = conn.createQuery(query1)
                .addParameter("entity_id", entity.getId())
                .executeUpdate()
                .getResult();
        System.out.println("Deleted values: " + results);
        // delete entity
        String query2 = "DELETE FROM " + entityTable + " WHERE id = :entity_id";
        int results2 = conn.createQuery(query2)
                .addParameter("entity_id", entity.getId())
                .executeUpdate()
                .getResult();
        System.out.println("Deleted entity: " + results2);

        return results2 > 0;
    }

    public boolean deleteEntities(Collection<EavEntity> entities) {
        if (entities.isEmpty()) {
            return true;
        }

        StringBuilder idList = new StringBuilder();
        for (EavEntity e : entities) {
            idList.append(e.getId()).append(",");
        }
        idList.deleteCharAt(idList.length() - 1);

        // delete all values for entities
        String query1 = "DELETE FROM " + valueTable + " WHERE entity_id IN (:entity_ids)";
        int results = conn.createQuery(query1)
                .addParameter("entity_ids", idList)
                .executeUpdate()
                .getResult();
        System.out.println("Deleted values: " + results);
        // delete entity
        String query2 = "DELETE FROM " + entityTable + " WHERE id IN (:entity_ids)";
        int results2 = conn.createQuery(query2)
                .addParameter("entity_ids", idList)
                .executeUpdate()
                .getResult();
        System.out.println("Deleted entities: " + results2);

        return results2 > 0;
    }
    // endregion entity

    // region attribute
    public List<EavAttribute> getAttributes(EavEntityType entityType) {
        String query = "SELECT * FROM " + attributeTable + " WHERE entity_type_id = " + entityType.getId();
        return conn.createQuery(query).executeAndFetch(EavAttribute.class);
    }

    public List<EavAttribute> getAttributes(EavEntity entity) {
        String query = "SELECT * FROM " + attributeTable + " WHERE entity_type_id = " + entity.getEntityTypeId();
        return conn.createQuery(query).executeAndFetch(EavAttribute.class);
    }

    public EavAttribute createAttribute(EavEntity entity, EavAttribute attribute) {
        // todo: call create_eav_attr(:entity_id, :attr)
        return attribute;
    }

    public boolean deleteAttribute(EavAttribute attribute) {
        // delete all values for attribute
        String query1 = "DELETE FROM " + valueTable + " WHERE attr_id = :attr_id";
        int results = conn.createQuery(query1)
                .addParameter("attr_id", attribute.getId())
                .executeUpdate()
                .getResult();
        System.out.println("Deleted values: " + results);
        // delete attribute
        String query2 = "DELETE FROM " + attributeTable + " WHERE id = :attr_id";
        int results2 = conn.createQuery(query2)
                .addParameter("attr_id", attribute.getId())
                .executeUpdate()
                .getResult();
        System.out.println("Deleted attribute: " + results2);

        return results2 > 0;
    }

    public boolean deleteAttributes(Collection<EavAttribute> attributes) {
        if (attributes.isEmpty()) {
            return true;
        }

        StringBuilder idList = new StringBuilder();
        for (EavAttribute a : attributes) {
            idList.append(a.getId()).append(",");
        }
        idList.deleteCharAt(idList.length() - 1);

        // delete all values for entities
        String query1 = "DELETE FROM " + valueTable + " WHERE attr_id IN (:attr_ids)";
        int results = conn.createQuery(query1)
                .addParameter("attr_ids", idList)
                .executeUpdate()
                .getResult();
        System.out.println("Deleted values: " + results);
        // delete entity
        String query2 = "DELETE FROM " + attributeTable + " WHERE id IN (:attr_ids)";
        int results2 = conn.createQuery(query2)
                .addParameter("attr_ids", idList)
                .executeUpdate()
                .getResult();
        System.out.println("Deleted attributes: " + results2);

        return results2 > 0;
    }
    // endregion attribute

    // region value
    public List<EavValue> getValues(EavEntity entity) {
        String query = "SELECT * FROM " + valueTable + " WHERE entity_id = " + entity.getId();
        return conn.createQuery(query).executeAndFetch(EavValue.class);
    }

    public boolean deleteValue(EavValue value) {
        String query1 = "DELETE FROM " + valueTable + " WHERE entity_id = :value_id";
        int result = conn.createQuery(query1)
                .addParameter("entity_id", value.getId())
                .executeUpdate()
                .getResult();

        return result > 0;
    }

    public boolean deleteValues(Collection<EavValue> values) {
        if (values.isEmpty()) {
            return true;
        }

        StringBuilder idList = new StringBuilder();
        for (EavValue v : values) {
            idList.append(v.getId()).append(",");
        }
        idList.deleteCharAt(idList.length() - 1);

        String query1 = "DELETE FROM " + valueTable + " WHERE id IN (:ids)";
        int results = conn.createQuery(query1)
                .addParameter("ids", idList)
                .executeUpdate()
                .getResult();

        return results > 0;
    }
    // endregion value

    // region view
    public List<EavView> getEverything() {
        String query = "SELECT * FROM all_existing_eav_data";
        return conn.createQuery(query).executeAndFetch(EavView.class);
    }
    // endregion view
}
