import React from "react";
import classes from "./LoginPage.module.css";
import NaverLoginButton from "../components/users/NaverLoginButton";
import GoogleLoginButton from "../components/users/GoogleLoginButton";
export default function LoginPage() {
  return (
    <div>
      <section className={classes.container}>
        <div className={classes.loginBox}>
          <img
            src="../../foruth-image.png"
            alt=""
            className={classes.loginImg}
          />
          <NaverLoginButton />
          <GoogleLoginButton />
        </div>
      </section>
    </div>
  );
}
