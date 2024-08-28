import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";

import EntityData from "./entityData";
import { fetchValues, openForm } from "../../store/eav";

const EntityContainer = () => {
  const dispatch = useDispatch();
  const entities = useSelector((state) => state.eav.entities);
  const [fetchedEntity, setFetchedEntity] = useState(0);

  function fetchData(id) {
    setFetchedEntity(id);
    dispatch(fetchValues(id));
  }

  function displayNoEntry() {
    if (entities.length < 1) return true;
    if (entities.length === 1) {
      let entity = entities[0].entity;
      if (entity === "-") return true;
    }
    return false;
  }

  return (
    <div className="entry-container">
      <div className="btn-ctn">
        <button onClick={() => dispatch(openForm("entity"))}>+ entity</button>
        <button onClick={() => dispatch(openForm("attr"))}>+ attribute</button>
      </div>
      {displayNoEntry() ? (
        <div className="eav-entry">
          <div className="label">No entries</div>
        </div>
      ) : (entities.map(e => {
        if (e.entity === "-") return null;
        return (
          <div className="eav-entry" key={"entity-" + e.id}>
            <div className="header">
              <div className="label">{e.entity}</div>
              <button onClick={() => fetchData(e.id)}>
                Fetch data
              </button>
            </div>
            {fetchedEntity === e.id ? <EntityData /> : null}
          </div>
        )
      }))}
    </div>
  )
}

export default EntityContainer;