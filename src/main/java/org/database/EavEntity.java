package org.database;

import lombok.Data;

@Data
public class EavEntity {
    private int id = 0;
    private String created_at = null;
    private String entity = null;
    private int entity_type_id = 0;
}
