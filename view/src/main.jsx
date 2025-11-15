import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { Main } from './components/Main';
import './css/index.css';
import { Header } from './components/Header';
import './css/Header.css'
import './css/Main.css'

import Products from './components/Products';

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <Products/>
  </StrictMode>,
)
