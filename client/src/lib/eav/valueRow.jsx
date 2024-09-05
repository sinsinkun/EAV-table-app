import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";

import { addValue, updateValue } from "../../store/eav";

const ValueRow = ({ data }) => {
  const dispatch = useDispatch();
  const [isEditing, setIsEditing] = useState(false);
  const [fvalue, setFValue] = useState("");
  const [dvalue, setDValue] = useState(null);

  function submitValue() {
    if (!fvalue && fvalue !== 0) return setIsEditing(false);
    const form = { ...data };
    switch (data.valueType) {
      case "STR":
        form.valueStr = fvalue;
        break;
      case "INT":
        form.valueInt = fvalue;
        break;
      case "FLOAT":
        form.valueFloat = fvalue;
        break;
      case "TIME":
        form.valueTime = fvalue;
        break;
      case "BOOL":
        form.valueBool = fvalue;
        break;
      default:
        break;
    }
    if (!data.valueId) dispatch(addValue(form));
    else if (data.allowMultiple) dispatch(addValue(form));
    else dispatch(updateValue(form));
    // clean up
    setDValue(fvalue);
    setIsEditing(false);
  }

  function handleInput(e) {
    const { value } = e.target;
    setFValue(value);
  }

  function handleCheck(e) {
    const { checked } = e.target;
    setFValue(checked);
  }

  useEffect(() => {
    switch (data.valueType) {
      case "STR":
        if (data.valueStr) {
          setFValue(data.valueStr);
          setDValue(data.valueStr);
        } else {
          setDValue("-");
        }
        break;
      case "INT":
        if (data.valueInt || data.valueInt === 0) {
          setFValue(data.valueInt);
          setDValue(String(data.valueInt));
        } else {
          setDValue("-")
        }
        break;
      case "FLOAT":
        if (data.valueFloat || data.valueFloat === 0) {
          setFValue(data.valueFloat);
          setDValue(String(data.valueFloat));
        } else {
          setDValue("-");
        }
        break;
      case "TIME":
        if (data.valueTime) {
          setFValue(data.valueTime);
          setDValue(data.valueTime);
        } else {
          setDValue("-");
        }
        break;
      case "BOOL":
        if (data.valueBool === true) {
          setFValue(true);
          setDValue("Yes");
        } else if (data.valueBool === false) {
          setFValue(false);
          setDValue("No");
        } else {
          setDValue("-");
        }
        break;
      default:
        break;
    }
  }, [])

  return (
    <div className="grid" style={{ textAlign: "left" }}>
      <div>{data.attr}</div>
      {isEditing ? (
        <div className="value-field-container">
          {data.valueType === "STR" && <input type="text" value={fvalue} onChange={handleInput} />}
          {data.valueType === "INT" && <input type="number" value={fvalue} onChange={handleInput} />}
          {data.valueType === "FLOAT" && <input type="number" value={fvalue} onChange={handleInput} />}
          {data.valueType === "TIME" && <input type="datetime-local" value={fvalue} onChange={handleInput} />}
          {data.valueType === "BOOL" && <input type="checkbox" checked={fvalue} onChange={handleCheck} />}
          <div style={{ flexGrow:1 }}></div>
          <button onClick={submitValue}>Add</button>
          <button onClick={() => setIsEditing(false)}>Cancel</button>
        </div>
      ) : (
        <div className="value-display-container" onClick={() => setIsEditing(true)}>
          {dvalue}
        </div>
      )}
    </div>
  )
}

export default ValueRow;