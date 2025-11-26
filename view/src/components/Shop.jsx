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
                <div className="sidebar-footer">
                    <div className="user-avatar"></div>
                    <div className="user-info">
                        <span className="user-name">Alex Morgan</span>
                        <span className="user-role">Administrator</span>
                    </div>
                </div>
            </aside>
            <div id="wrapper-main-shop-content">
                <div id="header-shop-content">
                    <h1 className="title" style={{ fontSize: "2em", margin: "3px" }}>
                        Good Morning, <span className="text-gradient">Jonhatan</span><br />
                    </h1>
                    <h4 style={{ position: "absolute", bottom: "15px", left: "20px" }}>Here´s what´s happening today</h4>
                    <input
                        type="text"
                        className="chat-input"
                        placeholder="Search..."
                        style={{ height: "50px", width: "300px", transform: "translateY(50%)", marginRight: "10px" }}
                    />
                </div>
                <div id="main-shop-content">
                    <div id="chat">
                        <div className="chat-header">
                            <div className="chat-status">
                                <span className="status-dot"></span>
                                <div>
                                    <div style={{ fontWeight: 600, fontSize: '0.95rem', color: "white" }}>Customer Support #402</div>
                                    <div style={{ fontSize: '0.75rem', color: '#94a3b8' }}>Online</div>
                                </div>
                            </div>
                            <i
                                className="fa-solid fa-ellipsis"
                                style={{ color: '#94a3b8', cursor: 'pointer' }}
                            ></i>
                        </div>
                        <div className="chat-messages">
                            <div className="message received">
                                Hola 👋, ¿tienen stock de Spotify Familiar?
                                <span className="timestamp">10:42 AM</span>
                            </div>
                            <div className="message sent">
                                ¡Hola! Sí, entrega inmediata. El código llega a tu correo.
                                <span className="timestamp">10:43 AM</span>
                            </div>
                            <div className="message received">
                                Perfecto, ¿aceptan criptomonedas?
                                <span className="timestamp">10:44 AM</span>
                            </div>
                            <div className="message sent">
                                Claro, aceptamos USDT y BTC.
                                <span className="timestamp">10:45 AM</span>
                            </div>
                        </div>
                        <div className="chat-input-area">
                            <div className="input-wrapper">
                                <input
                                    type="text"
                                    className="chat-input"
                                    placeholder="Write your message..."
                                />
                            </div>
                            <button className="btn-send">
                                <i className="fa-solid fa-paper-plane"></i>
                            </button>
                        </div>
                    </div>
                    <div id="content">
                        <div className="dashboard-menu">
                            <div className="item-menu">
                                <h3>Page views</h3>
                                <div className="value-row">
                                    <h1>16,431</h1>
                                    <div className="item-statistics">▲14%</div>
                                </div>
                                <h4>vs. 14,853 last period</h4>
                            </div>

                            <div className="item-menu">
                                <h3>Visitors</h3>
                                <div className="value-row">
                                    <h1>6,255</h1>
                                    <div className="item-statistics">▲8.4%</div>
                                </div>
                                <h4>vs. 5,732 last period</h4>
                            </div>

                            <div className="item-menu">
                                <h3>Visitors</h3>
                                <div className="value-row">
                                    <h1>6,255</h1>
                                    <div className="item-statistics">▲8.4%</div>
                                </div>
                                <h4>vs. 5,732 last period</h4>
                            </div>
                            <div className="item-menu">
                                <h3>Visitors</h3>
                                <div className="value-row">
                                    <h1>6,255</h1>
                                    <div className="item-statistics">▲8.4%</div>
                                </div>
                                <h4>vs. 5,732 last period</h4>
                            </div>
                        </div>
                        <div className="product-section">
                            <div className="product-card">
                                <div className="product-details">
                                    <div className="product-icon"><i className="fa-brands fa-spotify"></i></div>
                                    <div className="product-info">
                                        <h3>Spotify Premium</h3>
                                        <span>Plan Familiar - 1 Mes</span>
                                    </div>
                                </div>
                                <div className="price-tag">$10.00</div>
                            </div>
                            <div className="product-card">
                                <div className="product-details">
                                    <div
                                        className="product-icon"
                                        style={{ color: "#E50914", background: "rgba(229, 9, 20, 0.1)" }}
                                    >
                                        <i className="fa-solid fa-n"></i>
                                    </div>
                                    <div className="product-info">
                                        <h3>Netflix 4K</h3>
                                        <span>1 Pantalla - 30 Días</span>
                                    </div>
                                </div>
                                <div className="price-tag">$5.00</div>
                            </div>
                            <div className="product-card">
                                <div className="product-details">
                                    <div
                                        className="product-icon"
                                        style={{ color: "#00A8E1", background: "rgba(0, 168, 225, 0.1)" }}
                                    >
                                        <i className="fa-solid fa-play"></i>
                                    </div>
                                    <div className="product-info">
                                        <h3>Prime Video</h3>
                                        <span>Cuenta Completa - 6 Meses</span>
                                    </div>
                                </div>
                                <div className="price-tag">$25.00</div>
                            </div>
                            <div className="product-card">
                                <div className="product-details">
                                    <div
                                        className="product-icon"
                                        style={{ color: "#fff", background: "rgba(255, 255, 255, 0.1)" }}
                                    >
                                        <i className="fa-brands fa-apple"></i>
                                    </div>
                                    <div className="product-info">
                                        <h3>Apple Music</h3>
                                        <span>Código Canjeable</span>
                                    </div>
                                </div>
                                <div className="price-tag">$8.50</div>
                            </div>
                        </div>
                        <div style={{ height: "100%", width: "100%", backgroundColor: "olive" }}></div>
                    </div>
                </div>
            </div>
        </div>
    );
}
