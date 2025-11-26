import React from "react";
import StatsContainer from "./StatsContainer";
import FeaturesGrid from "./FeaturesGrid";
import { Header } from "./Header";
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
        <>
            <Header />
            <div className="main-block">
                <h1 className="title">
                    Reinventing the way <br /> you buy and
                    <span className="text-gradient"> win</span>
                </h1>
                <p className="subtitle">
                    Experience a revolutionary platform that transforms your purchasing
                    power into winning opportunities
                </p>
                <FeaturesGrid features={features} />
                <StatsContainer stats={stats} />

                <div className="collection-buttons">
                    <button className="button">Get started</button>
                    <button className="button-transparent">Book a demo</button>
                </div>
                <div className="marquee">
                    <div className="marquee-content">
                        <span>Free Shipping +$100</span> •{" "}
                        <span>New Weekly Drops</span> • <span>Exclusive Design</span> •{" "}
                        <span>Premium Quality</span> • <span>Free Shipping +$100</span> •{" "}
                        <span>New Weekly Drops</span> • <span>Exclusive Design</span> •{" "}
                        <span>Premium Quality</span>
                    </div>
                </div>
            </div>
        </>
    );
}
