package org.database;

import java.util.List;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class DBInterface<T> {
    private final Connection conn;
    private final Class<T> schema;

    public DBInterface(Class<T> schemaDef, String server, String dbName) {
        if (server.isEmpty() || dbName.isEmpty()) {
            throw new IllegalArgumentException("Required arguments not provided");
        }
        Sql2o db = new Sql2o(
                "jdbc:mysql://" + server + "/" + dbName + "?allowPublicKeyRetrieval=true",
                "root", "password"
        );
        try (Connection c = db.open()) {
            conn = c;
            schema = schemaDef;
        }
    }

    public List<T> get(String target) {
        if (target.isEmpty()) {
            throw new IllegalArgumentException("Query target not provided");
        }
        String query = "SELECT * FROM " + target;
        return conn.createQuery(query).executeAndFetch(this.schema);
    }
}
