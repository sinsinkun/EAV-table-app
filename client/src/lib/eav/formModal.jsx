import { useEffect, useState } from "react";
import { observer } from "mobx-react";

import { eav } from "../../store";

const FormModal = observer(() => {
  const [fields, setFields] = useState({});
  const [title, setTitle] = useState("");

  useEffect(() => {
    switch (eav.formType) {
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
  }, [eav.formType])

  if (!eav.formType) return null;
  return (
    <div className="modal-container">
      <div className="modal-form">
        <h3>{title}</h3>
        <button onClick={() => eav.closeForm()}>Close</button>
      </div>
    </div>
  )
})

export default FormModal;