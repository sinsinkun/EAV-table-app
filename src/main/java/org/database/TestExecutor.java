package org.database;

// playground class for experimenting with DBInterface
public class TestExecutor {
    public static EavInterface eav;

    public static void main(String[] args) {
        eav = new EavInterface("localhost:3306", "localdb");

        EavEntity entity = eav.getEntityById(1);
        System.out.println("Entity: " + entity);
        EavAttribute attr = eav.getAttributeById(5);
        System.out.println("Attr: " + attr);
        EavValue value = eav.getValueById(4);
        System.out.println("Value: " + value);
    }
}
