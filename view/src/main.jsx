import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './assets/css/index.css';
import {Main} from "./components/Main.jsx";
import SignIn from "./components/SignIn.jsx";


createRoot(document.getElementById('root')).render(
  <StrictMode>
    <SignIn/>
  </StrictMode>,
)
