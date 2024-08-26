import EntityContainer from "./entityContainer";
import EntityTypeTabs from "./entityTypeTabs";

function EavTable() {
  return (
    <div className="eav-table">
      <EntityTypeTabs />
      <EntityContainer />
    </div>
  )
}

export default EavTable;