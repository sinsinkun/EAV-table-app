package org.database;

public class DbSetup {
    public String server = "http://localhost:3306";
    public String dbName = "localdb";
    public String user = "root";
    public String password = "password";
    public String entityTypeTable = "eav_entity_types";
    public String entityTable = "eav_entities";
    public String attributeTable = "eav_attrs";
    public String valueTable = "eav_values";

    public boolean isValid() {
        return !server.isEmpty() &&
                !dbName.isEmpty() &&
                !user.isEmpty() &&
                !password.isEmpty() &&
                !entityTypeTable.isEmpty() &&
                !entityTable.isEmpty() &&
                !attributeTable.isEmpty() &&
                !valueTable.isEmpty();
    }
}
