import React, { useEffect } from "react";

export default function NaverLoginButton({}) {
  // TODO : .env 파일에 REACT_APP_NAVER_CLIENT_ID에 저장하기.
  const NAVER_CLIENT_ID = "0jXgPVDyLQu_ekRssB20";
  //TODO : 나중에 Redirect URI를 http://i9b108.p.ssafy.io:3000/ 로 바꾸기
  //TODO : redirect URI를 main에서 닉네임 설정 화면으로 연결하기
  const REDIRECT_URI = "http://i9b108.p.ssafy.io:3000/redirect";
  const NAVER_AUTH_URL = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${NAVER_CLIENT_ID}&state=STATE_STRING&redirect_uri=${REDIRECT_URI}`;

  const handleNaverLogin = () => {
    window.location.href = NAVER_AUTH_URL;
  };
  return <button onClick={handleNaverLogin}>네이버 로그인</button>;
}
