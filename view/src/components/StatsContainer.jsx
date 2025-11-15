import React from "react";
import StatItem from "./StatItem";
export default function StatsContainer({ stats }) {
    return (
        <div
            style={{
                display: "flex",
                gap: "3rem",
                margin: "1rem 0",
                flexWrap: "wrap",
                justifyContent: "center",
            }}
        >
            {stats.map((stat, index) => (
                <StatItem key={index} number={stat.number} label={stat.label} />
            ))}
        </div>
    );
}