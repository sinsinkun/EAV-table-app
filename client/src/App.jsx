import { BrowserRouter, Routes, Route } from 'react-router-dom';

import './store';
import './theme/App.css';
import Layout from './lib/layout';

import HomePage from './pages/home';
import FallbackPage from './pages/fallback';

function App() {
  return (
    <BrowserRouter>
      <Layout>
        <Routes>
          <Route exact path="/" element={<HomePage />} />
          <Route path="*" element={<FallbackPage />} />
        </Routes>
      </Layout>
    </BrowserRouter>
  )
}

export default App;
