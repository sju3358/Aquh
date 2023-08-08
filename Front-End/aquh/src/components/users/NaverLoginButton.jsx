import React, { useEffect } from "react";
import classes from "./NaverLoginButton.module.css";

export default function NaverLoginButton({}) {
  // TODO : .env 파일에 REACT_APP_NAVER_CLIENT_ID에 저장하기.
  const NAVER_CLIENT_ID = "0jXgPVDyLQu_ekRssB20";
  //TODO : 나중에 Redirect URI를 http://i9b108.p.ssafy.io:3000/ 로 바꾸기
  //TODO : redirect URI를 main에서 닉네임 설정 화면으로 연결하기
  const REDIRECT_URI_SERVER = "https://i9b108.p.ssafy.io:3000/redirect";
  const REDIRECT_URI_LOCAL = "https://localhost:3000/redirect";
  const NAVER_AUTH_URL = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${NAVER_CLIENT_ID}&state=STATE_STRING&redirect_uri=${REDIRECT_URI_SERVER}`;

  const handleNaverLogin = () => {
    window.location.href = NAVER_AUTH_URL;
  };
  return <button onClick={handleNaverLogin} className={classes.login}><img src="../../naver-login-white.png" alt="naver-login" className={classes.naverLogin} /></button>;
}
