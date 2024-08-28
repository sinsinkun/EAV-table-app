import { useEffect, useState } from "react";
import { useSelector, useDispatch } from "react-redux";

import { closeForm } from "../../store/eav";

const FormModal = () => {
  const dispatch = useDispatch();
  const formType = useSelector((state) => state.eav.formType);
  const [fields, setFields] = useState({});
  const [title, setTitle] = useState("");

  function handleSubmit(e) {
    e.preventDefault();
    console.log("submit", fields);
  }

  function handleInput(e) {
    const { name, value } = e.target;
    setFields(prev => ({ ...prev, [name]: value }));
  }

  function handleSelect(e) {

  }

  useEffect(() => {
    switch (formType) {
      case "entity":
        setTitle("New Entity");
        setFields({ entity: "", entityType: "" });
        break;
      case "attr":
        setTitle("New Attribute");
        setFields({ attr: "", valueType: "", allowMultiple: false });
        break;
      default:
        setTitle("Unknown");
        break;
    }
  }, [formType])

  function renderEntityFields() {
    return (
      <>
        <label htmlFor="entity">Name</label>
        <input type="text" name="entity" onChange={handleInput} />
      </>
    )
  }

  function renderAttrFields() {
    return (
      <>
        <label htmlFor="attr">Name</label>
        <input type="text" name="attr" onChange={handleInput} />
        <label htmlFor="valueType">Value Type</label>
        <select name="valueType" onSelect={handleSelect}>
          <option>String</option>
          <option>Int</option>
          <option>Float</option>
        </select>
      </>
    )
  }

  if (!formType) return null;
  return (
    <div className="modal-container">
      <form className="modal-form" onSubmit={handleSubmit}>
        <h3>{title}</h3>
        {formType === "entity" && renderEntityFields()}
        {formType === "attr" && renderAttrFields()}
        <div className="btn-ctn">
          <button type="submit">Add</button>
          <button onClick={() => dispatch(closeForm())}>Close</button>
        </div>
      </form>
    </div>
  )
}

export default FormModal;