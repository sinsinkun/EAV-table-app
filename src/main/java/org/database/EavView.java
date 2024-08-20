package org.database;

import lombok.Data;

import java.time.Instant;

@Data
public class EavView {
    // entity related
    private Integer entity_type_id = null;
    private String  entity_type    = null;
    private Integer entity_id      = null;
    private String  entity         = null;

    // attribute related
    private Integer attr_id        = null;
    private String  attr           = null;
    private String  value_type     = null;
    private Boolean allow_multiple = null;

    // value related
    private Integer value_id    = null;
    private String  created_at  = null;
    private String  value_str   = null;
    private Integer value_int   = null;
    private Float   value_float = null;
    private Instant value_time  = null;
    private Boolean value_bool  = null;
}
