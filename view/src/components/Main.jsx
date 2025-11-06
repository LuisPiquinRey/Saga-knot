import React from "react";
import { Counter } from "./Counter";
export function Main() {
    return (
        <div className="main-block">
            <h1 className="title">
                Reinventing the way <br /> you buy and
                <span className="text-gradient"> win</span>
            </h1>
            <div className="collection-buttons">
                <button className="button">Get started</button>
                <button className="button-transparent">Book a demo</button>
            </div>
            <div className="main-block-text">
                <p style={{ whiteSpace: "pre-line", fontWeight:"bold" , fontSize:"2em" }}>
    Collaboration and curiosity,
    allowing us to uncover fresh
    perspectives and create
    work that truly resonates.
                </p>
                <div className="terminal"></div>
                <div id="data-project">
                    <h1>Project</h1>
                    <Counter numberToReach={200} id={"data-project"}/>
                </div>
            </div>
        </div>
    );
}