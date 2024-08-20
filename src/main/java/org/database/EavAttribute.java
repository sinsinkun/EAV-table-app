package org.database;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@EqualsAndHashCode
@Table(name = "eav_attrs")
@SuppressWarnings("unused")
public class EavAttribute {

    @Column(name = "id", unique = true, nullable = false)
    private int id = 0;

    @Column(name = "created_at")
    private String createdAt = null;

    @Column(name = "attr")
    private String attr = null;

    @Column(name = "entity_type_id", nullable = false)
    private int entityTypeId = 0;

    @Column(name = "value_type")
    private String valueType = null;

    @Column(name = "allow_multiple", nullable = false)
    private boolean allowMultiple = false;

    // custom getter for valueType
    public ValueType getValueType() {
        return ValueType.fromString(valueType);
    }

    // custom setter for valueType
    public void setValueType(ValueType v) {
        valueType = v.getValue();
    }
}
