import React, { useEffect } from "react";
import classes from "./NaverLoginButton.module.css";
import SERVER_URL from "../../config/SERVER_URL";
export default function NaverLoginButton({}) {
  // TODO : .env 파일에 REACT_APP_NAVER_CLIENT_ID에 저장하기.
  const NAVER_CLIENT_ID = "sOLMKlOcIu3pqoFbtMey";

  
  const NAVER_AUTH_URL = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${NAVER_CLIENT_ID}&state=STATE_STRING&redirect_uri=${SERVER_URL}/redirect`;
  console.log(NAVER_AUTH_URL);

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
