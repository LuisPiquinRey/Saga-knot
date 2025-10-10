import React from 'react';

const Icon = () => {
    return (
        <header className="header">
            <img src="menu.svg" alt="menu" />
            <h1 id='title'>Knot shop</h1>
            <input className='search'></input>
            <div className="right-items">
                <span className="material-symbols-outlined">
                    person
                </span>
                <span className="material-symbols-outlined">
                    shopping_cart
                </span>
            </div>
        </header>
    );
};

export default Icon;
