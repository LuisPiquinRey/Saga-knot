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
        </div>
    );
}