import React from "react";
export default function SignIn() {
    return (
        <div className="main-block">
            <div className="basic-block">
                <div className="logo-container" style={{ height: "80px" }}>
                    <img
                        className="logo"
                        src="/photos/icon.png"
                        style={{ height: "200px" }}
                        alt="Knot logo"
                    />
                </div>
                <h1 className="title" style={{ fontSize: "2em" }}>
                    Sign in with
                    <span className="text-gradient"> email</span>
                </h1>
                <p className="subtitle" style={{ color: "white", fontWeight: "bold" }}>
                    Make a new doc to bring your words,
                    <br /> data, and teams together. For free.
                </p>
                <form
                    style={{
                        display: "flex",
                        flexDirection: "column",
                        gap: "10px",
                        margin: "40px",
                    }}
                >
                    <input
                        type="email"
                        placeholder={"Email"}
                        className="input-rectangle"
                        required
                    />
                    <input
                        type="password"
                        placeholder={"Password"}
                        className="input-rectangle"
                        required
                    />
                    <h4 style={{ color: "white", fontSize: "1em", textAlign: "end" }}>
                        Forget your password?
                    </h4>
                    <input
                        type="submit"
                        value={"Get started"}
                        className="input-rectangle"
                        style={{
                            backgroundColor: "#AB5ED0",
                            color: "white",
                            fontWeight: "bold",
                            marginTop: "0px",
                        }}
                    />
                </form>
                <div className="divider-text">Or sign in with</div>
                <div className="socialButtons">
                    <button className="socialButton">G</button>
                    <button className="socialButton">f</button>
                    <button className="socialButton">in</button>
                </div>
            </div>
            <h3>
                Join over <span className="text-gradient">2M</span> global social media
                users
            </h3>
            <div className="circle-container">
                <div className="circle"></div>
                <div className="circle"></div>
                <div className="circle"></div>
                <div className="circle"></div>
            </div>
        </div>
    );
}
