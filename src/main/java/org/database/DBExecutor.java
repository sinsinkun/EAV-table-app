package org.database;

import java.util.List;

public class DBExecutor {
    public static void main(String[] args) {
        DBInterface<EavData> eavInterface = new DBInterface<>(EavData.class, "localhost:3306", "localdb");
        List<EavData> output = eavInterface.get("all_existing_eav_data");
        for (EavData data : output) {
            System.out.println(data.print());
        }
    }
}
