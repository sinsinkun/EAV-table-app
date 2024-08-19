package org.database;

import java.util.List;

// playground class for experimenting with DBInterface
public class TestExecutor {
    public static EavInterface eav;

    public static void main(String[] args) {
        eav = new EavInterface("localhost:3306", "localdb");
        List<EavEntity> entities = eav.getEntities();
        List<EavValue> values = eav.getValues(entities.get(0));

        System.out.println("Output: " + values);
    }
}
