import React from "react";
export default function CreateAccount(){
    return(
        <div className="page-alternative">
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
                    Create your
                    <span className="text-gradient"> account</span>
                </h1>
                <p className="subtitle" style={{ color: "white", fontWeight: "bold" }}>
                    Join millions of users and start <br/>
                    organizing your ideas today.
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
                        type="text"
                        placeholder={"Username"}
                        className="input-rectangle"
                        required
                    />
                    <input
                        type="number"
                        placeholder={"Phone Number"}
                        className="input-rectangle"
                        required
                    />
                    <input
                        type="password"
                        placeholder={"Password"}
                        className="input-rectangle"
                        required
                    />
                    <p style={{ 
                        color: "rgba(255, 255, 255, 0.6)", 
                        fontSize: "12px",
                        textAlign: "center",
                        maxWidth: "350px",
                        margin: "10px 0"
                    }}>
                        By creating an account, you agree to our{" "}
                        <span className="link-text">Terms of Service</span> and{" "}
                        <span className="link-text">Privacy Policy</span>
                    </p>
                    <input
                        type="submit"
                        value={"Create Account"}
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
                <p style={{ 
                    color: "rgba(255, 255, 255, 0.7)", 
                    marginTop: "10px",
                    fontSize: "14px"
                }}>
                    Already have an account?{" "}
                    <span className="link-text">Sign in</span>
                </p>
            </div>
        </div>
    );
}