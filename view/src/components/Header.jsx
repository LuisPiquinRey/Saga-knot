import React from "react";

export function Header() {
    return (
        <nav className="navbar">
            <div className="logo-container">
                <img className="logo" src="icon.png" alt="Knot logo" />
            </div>
            <ul className="nav-links">
                <li><a href="#">Inicio</a></li>
                <li><a href="#">Acerca</a></li>
                <li><a href="#">Servicios</a></li>
                <li><a href="#">Blog</a></li>
                <li><a href="#">Contacto</a></li>
            </ul>
        </nav>
    );
}