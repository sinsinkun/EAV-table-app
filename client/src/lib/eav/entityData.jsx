import { observer } from "mobx-react";
import { toJS } from "mobx";

import { eav } from "../../store";

const EntityData = observer(() => {
  if (eav.loading) return <div className="value-container">Loading...</div>
  if (toJS(eav.values).length < 1) return null;
  return (
    <div className="value-container">
      {toJS(eav.values).map(v => {

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

        return (
          <div className="grid" key={v.valueId} style={{ textAlign: "left" }}>
            <div>{v.attr}</div>
            <div>{valueText}</div>
          </div>
        )
      })}
    </div>
  )
})

export default EntityData;