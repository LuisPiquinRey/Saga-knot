import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { Main } from './components/Main';
import './css/index.css';
import { Header } from './components/Header';
import './css/Header.css'
import './css/Main.css'
import Error404 from './components/Error404';

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <Error404/>
  </StrictMode>,
)
