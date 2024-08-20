package org.database;

// playground class for experimenting with DBInterface
public class TestExecutor {
    public static EavInterface eav;

    public static void main(String[] args) {
        eav = new EavInterface("localhost:3306", "localdb");

        EavEntityType entityType = eav.createEntityType("temp");
        System.out.println(entityType);
    }
}
