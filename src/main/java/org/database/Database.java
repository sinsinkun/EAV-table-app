package org.database;

import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class Database {
//    private final Sql2o db;
    private final Connection conn;

    public Database() {
        Sql2o db = new Sql2o("jdbc:mysql://localhost:3306/localdb?allowPublicKeyRetrieval=true", "root", "password");
        try (Connection c = db.open()) {
            conn = c;
        }
    }

    public List<EavData> fetchAllEav() {
        String query = "select * from all_eav_data";
        return conn.createQuery(query).executeAndFetch(EavData.class);
    }

    public List<Integer> fetchIds() {
        String query = "select id from eav_table";
        return conn.createQuery(query).executeAndFetch(Integer.class);
    }
}
