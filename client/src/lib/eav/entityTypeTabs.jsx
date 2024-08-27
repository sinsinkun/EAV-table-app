import { useEffect, useState } from "react";
import { observer } from "mobx-react";
import { toJS } from "mobx";

import { eav } from "../../store";

const EntityTypeTabs = observer(() => {
  const [activeTab, setActiveTab] = useState(0);

  function loadEntities(id) {
    setActiveTab(id);
    eav.clearValues();
    eav.fetchEntities(id);
  }

  useEffect(() => {
    if (!eav.connected) eav.connect();
    else if (!eav.loading) eav.fetchEntityTypes();
    // eslint-disable-next-line
  }, [eav.connected])

  if (!eav.connected) return <div className="tab-container">DB not connected</div>
  return (
    <div className="tab-container">
      {toJS(eav.entityTypes).map(et => {
        let className = "tab";
        if (activeTab == et.id) className += " selected";
        return (
          <div className={className} key={et.id} onClick={() => loadEntities(et.id)}>
            {et.entityType}
          </div>
        )
      })}
    </div>
  )
});

export default EntityTypeTabs;