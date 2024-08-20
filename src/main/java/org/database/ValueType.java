package org.database;

import lombok.Getter;

@Getter
public enum ValueType {
    STR("str"),
    INT("int"),
    FLOAT("float"),
    TIME("time"),
    BOOL("bool");

    private final String value;
    ValueType (String value) {
        this.value = value;
    }

    public static ValueType fromString(String s) {
        if (s.isEmpty()) return null;
        for (ValueType vt : ValueType.values()) {
            if (vt.value.equals(s)) {
                return vt;
            }
        }
        throw new IllegalArgumentException("Err: Could not map value to ValueType: " + s);
    }
}
