import React from "react";
import { Counter } from "./Counter";
import StatsContainer from "./StatsContainer";
import FeaturesGrid from "./FeaturesGrid";
export function Main() {
    const features = [
        {
            icon: "⚡",
            title: "Lightning Fast",
            text: "Instant transactions and rewards",
        },
        {
            icon: "🎯",
            title: "Smart Rewards",
            text: "AI-powered personalization",
        },
        {
            icon: "🔒",
            title: "Secure",
            text: "Bank-level encryption",
        },
    ];

    const stats = [
        { number: "100K+", label: "Active Users" },
        { number: "$2M+", label: "Rewards Earned" },
        { number: "99.9%", label: "Satisfaction" },
    ];
    return (
        <div className="main-block">
            <h1 className="title">
                Reinventing the way <br /> you buy and
                <span className="text-gradient"> win</span>
            </h1>
            <p
                style={{
                    color: "rgba(255, 255, 255, 0.7)",
                    fontSize: "1.2em",
                    maxWidth: "600px",
                    textAlign: "center",
                    lineHeight: "1.6",
                    margin: "-1rem 0 0 0",
                }}
            >
                Experience a revolutionary platform that transforms your purchasing
                power into winning opportunities
            </p>
            <FeaturesGrid features={features}/>
            <StatsContainer stats={stats} />

            <div className="collection-buttons">
                <button className="button">Get started</button>
                <button className="button-transparent">Book a demo</button>
            </div>
        </div>
    );
}
