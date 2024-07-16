package org.database;

public class EavData {
    public String entity_type;
    public String entity;
    public String attr;
    public int value_id;
    public String created_at;
    public String value_type;
    public String value_str;
    public int value_int;
    public float value_float;
    public String value_time;
    public boolean value_bool;

    public String print() {
        String out = "EavData: {\n";

        out += "  entity_type: " + entity_type + ",\n"
                + "  entity: " + entity + ",\n"
                + "  attr: " + attr + ",\n"
                + "  value_type: " + value_type + ",\n";

        // if no value id, then exit early
        if (value_id == 0) {
            out += "}\n";
            return out;
        }

        out += "  value_id: " + value_id + ",\n"
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