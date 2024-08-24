package org.access;

import lombok.Data;

@Data
public class DbAccess {
    private String host;
    private String dbName;
    private String user;
    private String password;
}
