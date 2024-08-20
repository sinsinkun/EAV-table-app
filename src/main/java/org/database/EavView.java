package org.database;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("unused")
@Data
@EqualsAndHashCode
public class EavView {
    // entity related
    @Column(name = "entity_type_id")
    private Integer entityTypeId = null;
    @Column(name = "entity_type")
    private String  entityType = null;
    @Column(name = "entity_id")
    private Integer entityId = null;
    @Column(name = "entity")
    private String  entity = null;

    // attribute related
    @Column(name = "attr_id")
    private Integer attrId = null;
    @Column(name = "attr")
    private String  attr = null;
    @Column(name = "value_type")
    private String  valueType = null;
    @Column(name = "allow_multiple")
    private Boolean allowMultiple = null;

    // value related
    @Column(name = "value_id")
    private Integer value_id = null;
    @Column(name = "created_at")
    private String  created_at = null;
    @Column(name = "value_str")
    private String  valueStr = null;
    @Column(name = "value_int")
    private Integer valueInt = null;
    @Column(name = "value_float")
    private Float   valueFloat = null;
    @Column(name = "value_time")
    private String valueTime  = null;
    @Column(name = "value_bool")
    private Boolean valueBool  = null;

    // region custom getters/setters
    public ValueType getValueType() {
        if (valueType == null || valueType.isEmpty()) return null;
        return ValueType.fromString(valueType);
    }

    public void setValueType(ValueType v) {
        valueType = v.getValue();
    }

    public Instant getValueTime() {
        if (valueTime == null || valueTime.isEmpty()) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(valueTime, formatter);
        return localDateTime.toInstant(ZoneOffset.UTC);
    }

    public void setValueTime(Instant i) {
        valueTime = i.toString();
    }
    // endregion custom getters/setters

    public String toCondensedString() {
        StringBuilder out = new StringBuilder("EavView: { ");
        out.append("entityType: ").append(this.getEntityType()).append(", ");
        out.append("entity: ").append(this.getEntity()).append(", ");
        out.append("attribute: ").append(this.getAttr()).append(", ");
        out.append("value: ");
        switch (this.getValueType()) {
            case STR:
                out.append(this.getValueStr());
                break;
            case INT:
                out.append(this.getValueInt());
                break;
            case FLOAT:
                out.append(this.getValueFloat());
                break;
            case TIME:
                out.append(this.getValueTime());
                break;
            case BOOL:
                out.append(this.getValueBool());
                break;
            default:
                out.append("null");
                break;
        }
        out.append(" }\n");

        return out.toString();
    }
}
