package org.database;

import org.util.Tuple;

// playground class for experimenting with DBInterface
public class TestExecutor {
    public static EavInterface eav;

    public static void main(String[] args) {
        eav = new EavInterface("localhost:3306", "localdb");
        Tuple<EavEntityType, EavEntity> newEnt = eav.createEntity("movie", "Dracula");
    }
}
