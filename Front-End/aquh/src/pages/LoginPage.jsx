import React from "react";
import classes from "./LoginPage.module.css";
import NaverLoginButton from "../components/users/NaverLoginButton";
import GoogleLoginButton from "../components/users/GoogleLoginButton";
export default function LoginPage() {
  return (
    <div>
      
      <section className={classes.container}>
        <p className={classes.ment}>우리들의 취미공간</p>
        <img className={classes.AquhLogo} src="../../aquh-logo.png" alt="아쿠아 로고" />
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
