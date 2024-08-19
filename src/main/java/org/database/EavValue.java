package org.database;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@EqualsAndHashCode
@Table(name = "eav_values")
public class EavValue {

    @Column(name = "id", unique = true, nullable = false)
    private int id = 0;

    @Column(name = "created_at")
    private String createdAt = null;

    @Column(name = "entity_id", nullable = false)
    private int entityId = 0;

    @Column(name = "attr_id", nullable = false)
    private int attrId = 0;

    @Column(name = "value_str")
    private String valueStr = null;

    @Column(name = "value_int")
    private Integer valueInt = null;

    @Column(name = "value_float")
    private Float valueFloat = null;

    @Column(name = "value_time")
    private String valueTime = null;

    @Column(name = "value_bool")
    private Boolean valueBool = null;
}
