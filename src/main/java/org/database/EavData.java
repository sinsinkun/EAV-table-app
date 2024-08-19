package org.database;

public class EavData {
    public Integer entity_type_id;
    public String entity_type;
    public Integer entity_id;
    public String entity;
    public Integer attr_id;
    public String attr;
    public Integer id;
    public String created_at;
    public String value_type;
    public Boolean allow_multiple;
    public String value_str;
    public Integer value_int;
    public Float value_float;
    public String value_time;
    public Boolean value_bool;

    public String print() {
        String out = "EavData: {\n";

        out += "  entity_type: " + entity_type + ",\n"
                + "  entity: " + entity + ",\n"
                + "  attr: " + attr + ",\n"
                + "  value_type: " + value_type + ",\n"
                + "  allow_multiple: " + allow_multiple + ",\n";

        // if no value id, then exit early
        if (id == null) {
            out += "}\n";
            return out;
        }

        out += "  value_id: " + id + ",\n"
                + "  created_at: " + created_at + ",\n";

        switch (value_type) {
            case "str":
                out += "  value: " + value_str + ",\n";
                break;
            case "int":
                out += "  value: " + value_int + ",\n";
                break;
            case "float":
                out += "  value: " + value_float + ",\n";
                break;
            case "time":
                out += "  value: " + value_time + ",\n";
                break;
            case "bool":
                out += "  value: " + value_bool + ",\n";
                break;
            default:
                break;
        }

        out += "}\n";
        return out;
    }
}