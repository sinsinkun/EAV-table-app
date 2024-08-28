import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { Provider } from 'react-redux';

import './theme/App.css';
import Layout from './lib/layout';
import store from "./store";

import HomePage from './pages/home';
import FallbackPage from './pages/fallback';

function App() {
  return (
    <BrowserRouter>
      <Provider store={store}>
        <Layout>
          <Routes>
            <Route exact path="/" element={<HomePage />} />
            <Route path="*" element={<FallbackPage />} />
          </Routes>
        </Layout>
      </Provider>
    </BrowserRouter>
  )
}

export default App;
