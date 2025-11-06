import { useState, useEffect } from "react";

export function Counter({ numberToReach, id }) {
    const [count, setCount] = useState(0);
    const [isVisible, setIsVisible] = useState(false);

    useEffect(() => {
        const element = document.getElementById(id);
        if (!element) return;

        const observer = new IntersectionObserver(
            (entries) => {
                if (entries[0].isIntersecting) {
                    setIsVisible(true);
                    observer.disconnect(); 
                }
            },
            { threshold: 0.3 } 
        );

        observer.observe(element);
        return () => observer.disconnect();
    }, [id]);
    useEffect(() => {
        if (!isVisible) return;

        let start = 0;
        const timer = setInterval(() => {
            start += 1;
            setCount(start);
            if (start >= numberToReach) clearInterval(timer);
        }, 10); 

        return () => clearInterval(timer);
    }, [isVisible, numberToReach]);

    return (
        <div id={id} style={{ textAlign: "center", color: "white" }}>
            <h1>{count}</h1>
        </div>
    );
}