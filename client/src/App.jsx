import { BrowserRouter, Routes, Route, redirect } from 'react-router-dom';
import { Provider } from 'react-redux';

import './theme/App.css';
import Layout from './lib/layout';
import store from "./store";

import HomePage from './pages/home';
import FallbackPage from './pages/fallback';

function Redirect({ url }) {
  redirect(url);
  return null;
}

function App() {
  return (
    <BrowserRouter>
      <Provider store={store}>
        <Layout>
          <Routes>
            <Route exact path="/" element={<HomePage />} />
            <Route path="/index.html" element={<Redirect url="/" />} />
            <Route path="*" element={<FallbackPage />} />
          </Routes>
        </Layout>
      </Provider>
    </BrowserRouter>
  )
}

export default App;
