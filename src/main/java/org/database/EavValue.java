package org.database;

import lombok.Data;

@Data
public class EavValue {
    private int id = 0;
    private String created_at = null;
    private int entity_id = 0;
    private int attr_id = 0;
    private String value_str = null;
    private Integer value_int = null;
    private Float value_float = null;
    private String value_time = null;
    private Boolean value_bool = null;
}
