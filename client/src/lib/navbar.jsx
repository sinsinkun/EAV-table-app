import { Link } from "react-router-dom";

function Navbar() {
  return (
    <nav>
      <div className="left">
        Data Manager
      </div>
      <div className="right">
        <Link to="/">Home</Link>
        <Link to="/settings">Settings</Link>
      </div>
    </nav>
  )
}

export default Navbar;