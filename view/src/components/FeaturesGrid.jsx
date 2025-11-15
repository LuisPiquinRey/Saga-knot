import React from "react";
import FeatureCard from "./FeatureCard";
export default function FeaturesGrid({ features }) {
    return (
        <div
            style={{
                display: "grid",
                gridTemplateColumns: "repeat(auto-fit, minmax(200px, 1fr))",
                gap: "1.5rem",
                margin: "1rem 0",
                maxWidth: "800px",
                width: "100%",
            }}
        >
            {features.map((feature, index) => (
                <FeatureCard
                    key={index}
                    icon={feature.icon}
                    title={feature.title}
                    text={feature.text}
                />
            ))}
        </div>
    );
}