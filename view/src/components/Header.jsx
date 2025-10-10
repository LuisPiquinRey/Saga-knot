import React from 'react';

const Header = () => {
    return (
        <header className="header">
            <img src="menu.svg" />
            <h1>Knot shop</h1>
            <div className="right-items">  
                <img src="search.svg" />
                <img src="person.svg" />
                <img src="cart.svg" />
            </div>
        </header>
    );
};


export default Header;