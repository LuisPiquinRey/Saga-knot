import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { Main } from './components/Main';
import './assets/css/index.css';
import { Header } from './components/Header';
import './assets/css/Header.css'
import './assets/css/Main.css'
import './assets/css/SignIn.css'
import SignIn from './components/SignIn';

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <SignIn/>
  </StrictMode>,
)
