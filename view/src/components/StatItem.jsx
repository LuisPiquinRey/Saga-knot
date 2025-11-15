import React from "react";
export default function StatItem({ number, label }) {
    return (
        <div style={{ textAlign: "center" }}>
            <div className="stat-item">
                {number}
            </div>
            <div
                style={{
                    color: "rgba(255, 255, 255, 0.7)",
                    fontSize: "0.9em",
                    marginTop: "0.5rem",
                }}
            >
                {label}
            </div>
        </div>
    );
}