import React from "react";
export default function Shop() {
    return (
        <div className="shop-block">
            <aside className="aside-menu">
                <div className="logo-container">
                    <img className="logo" src="/photos/icon.png" alt="Knot logo" />
                </div>
                <div id="menu">
                    <div className="menu-category">Main menu</div>
                    <ul className="menu-list">
                        <li>
                            <a href="#" class="menu-item">
                                <i class="fa-solid fa-house"></i>
                                <span>Dashboard</span>
                            </a>
                        </li>
                        <li>
                            <a href="#" class="menu-item active">
                                <i class="fa-solid fa-bag-shopping"></i>
                                <span>Products</span>
                            </a>
                        </li>
                        <li>
                            <a href="#" class="menu-item">
                                <i class="fa-solid fa-chart-simple"></i>
                                <span>Statistics</span>
                            </a>
                        </li>
                        <li>
                            <a href="#" class="menu-item">
                                <i class="fa-solid fa-users"></i>
                                <span>Clients</span>
                            </a>
                        </li>
                    </ul>
                </div>
                <div id="chat">

                </div>
            </aside>
        </div>
    );
}