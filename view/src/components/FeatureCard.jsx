import React from "react";

export default function FeatureCard({ icon, title, text }) {
    const [isHovered, setIsHovered] = React.useState(false);

    return (
        <div className="feature-card"
            style={{
                transform: isHovered ? "translateY(-5px)" : "translateY(0)",
                borderColor: isHovered
                    ? "rgba(183, 108, 243, 0.5)"
                    : "rgba(255, 255, 255, 0.1)",
                boxShadow: isHovered ? "0 10px 30px rgba(183, 108, 243, 0.2)" : "none",
            }}
            onMouseEnter={() => setIsHovered(true)}
            onMouseLeave={() => setIsHovered(false)}
        >
            <div style={{ fontSize: "2.5em", marginBottom: "0.5rem" }}>{icon}</div>
            <h3
                style={{
                    color: "white",
                    fontSize: "1.1em",
                    fontWeight: "600",
                    margin: "0 0 0.5rem 0",
                }}
            >
                {title}
            </h3>
            <p
                style={{
                    color: "rgba(255, 255, 255, 0.6)",
                    fontSize: "0.9em",
                    lineHeight: "1.4",
                    margin: 0,
                }}
            >
                {text}
            </p>
        </div>
    );
}
