package org.database;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@EqualsAndHashCode
@Table(name = "eav_attrs")
public class EavAttribute {

    @Column(name = "id", unique = true, nullable = false)
    public int id = 0;

    @Column(name = "created_at")
    public String createdAt = null;

    @Column(name = "attr")
    public String attr = null;

    @Column(name = "entity_type_id", nullable = false)
    public int entityTypeId = 0;

    @Column(name = "value_type")
    public String valueType = null;

    @Column(name = "allow_multiple", nullable = false)
    public boolean allowMultiple = false;
}
