package org.database;

import java.util.List;

public class DBExecutor {
    public static void main(String[] args) {
        Database db = new Database();

        List<EavData> eavData = db.fetchAllEav();
        System.out.println(eavData.get(8).print());

    }
}
