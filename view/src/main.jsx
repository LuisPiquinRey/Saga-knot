import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import Header from './components/Header';
import './css/Header.css';
import Footer from './components/Footer';
import './css/Footer.css'
import Carousel from './components/Carousel';
import Main from './components/Main';

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <Header/>
    <Footer/>
    <Carousel/>
    <Main/>
  </StrictMode>,
)
