package org.database;

// playground class for experimenting with DBInterface
public class TestExecutor {
    public static EavInterface eav;

    public static void main(String[] args) {
        eav = new EavInterface(new DbSetup());

        EavEntityType a = eav.createEntityType("movie");
        System.out.println(a);
    }
}
