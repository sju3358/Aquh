import React, { useEffect } from "react";
import classes from "./NaverLoginButton.module.css";
export default function NaverLoginButton({}) {

   
  const NAVER_AUTH_URL = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${process.env.REACT_APP_NAVER_CLIENT_ID}&state=STATE_STRING&redirect_uri=${process.env.REACT_APP_LOGIN_SERVER_URL}/redirectN`;

  const handleNaverLogin = () => {
    window.location.href = NAVER_AUTH_URL;
  };
  return (
    <button onClick={handleNaverLogin} className={classes.login}>
      <img
        src='../../naver-login-white.png'
        alt='naver-login'
        className={classes.naverLogin}
      />
    </button>
  );
}
