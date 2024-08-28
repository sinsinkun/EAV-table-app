import { useSelector } from "react-redux";

const EntityData = () => {
  const valuesData = useSelector((state) => state.eav.values);

  function convertValueToText(v) {
    let valueText = "null";
    switch (v.valueType) {
      case "STR":
        valueText = v.valueStr || "-";
        break;
      case "INT":
        valueText = (v.valueInt || v.valueInt === 0) ? String(v.valueInt) : "-";
        break;
      case "FLOAT":
        valueText = (v.valueFloat || v.valueFloat === 0) ? String(v.valueFloat) : "-";
        break;
      case "TIME":
        valueText = v.valueTime || "-";
        break;
      case "BOOL":
        valueText = v.valueBool ? "Yes" : "No";
        break;
      default:
        break;
    }
    return valueText;
  }

  if (valuesData.length < 1) return (
    <div className="value-container">No attributes found</div>
  )
  return (
    <div className="value-container">
      {valuesData.map(v => {
        return (
          <div className="grid" key={"value-" + v.attrId + v.valueId} style={{ textAlign: "left" }}>
            <div>{v.attr}</div>
            <div>{convertValueToText(v)}</div>
          </div>
        )
      })}
    </div>
  )
}

export default EntityData;