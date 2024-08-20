package org.database;

import java.util.List;

// playground class for experimenting with DBInterface
public class TestExecutor {
    public static EavInterface eav;

    public static void main(String[] args) {
        eav = new EavInterface("localhost:3306", "localdb");

        List<EavView> views = eav.getEveryValue();

        System.out.println(views.get(0).toCondensedString() + views.get(1).toCondensedString());
    }
}
