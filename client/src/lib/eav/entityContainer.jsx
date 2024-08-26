import { useState } from "react";
import { observer } from "mobx-react";
import { toJS } from "mobx";

import { eav } from "../../store";
import EntityData from "./entityData";


const EntityContainer = observer(() => {
  const [fetchedEntity, setFetchedEntity] = useState(0);

  function fetchData(id) {
    setFetchedEntity(id);
    eav.fetchValues(id);
  }

  function displayNoEntry() {
    const arr = toJS(eav.entities);
    if (arr.length < 1) return true;
    if (arr.length === 1) {
      let entity = arr[0].entity;
      if (entity === "-") return true;
    }
    return false;
  }

  if (eav.loading) return <div className="entry-container">Loading...</div>
  if (displayNoEntry()) return (
    <div className="entry-container">
      <div className="eav-entry">
        <div className="label">No entries</div>
      </div>
    </div>
  )
  return (
    <div className="entry-container">
      {toJS(eav.entities).map(e => {
        if (e.entity === "-") return null;
        return (
          <div className="eav-entry" key={e.id}>
            <div className="header">
              <div className="label">{e.entity}</div>
              <button onClick={() => fetchData(e.id)}>
                Fetch data
              </button>
            </div>
            {fetchedEntity === e.id ? <EntityData /> : null}
          </div>
        )
      })}
    </div>
  )
});

export default EntityContainer;