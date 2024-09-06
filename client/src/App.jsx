import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { Provider } from 'react-redux';

import './theme/App.css';
import store from "./store";

import HomePage from './pages/home';
import FallbackPage from './pages/fallback';

function App() {
  return (
    <BrowserRouter>
      <Provider store={store}>
        <Routes>
          <Route exact path="/" element={<HomePage />} />
          <Route path="*" element={<FallbackPage />} />
        </Routes>
      </Provider>
    </BrowserRouter>
  )
}

export default App;
