import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

import https from "../utils/https";
export default function RedirectURI(props) {
  const navigate = useNavigate();

  useEffect(() => {
    let code = new URL(window.location.href).searchParams.get("code");
    let state = new URL(window.location.href).searchParams.get("state");
    console.log(code);
    console.log(state);

    const data = {
      code: code,
      state: state,
    }
    https.post("/api/v1/member/auth/naver",data)
      .then((res) => {
        if (res.status === 200) {
          localStorage.setItem("access_token", res.data.data.access_token);
          localStorage.setItem("refresh_token", res.data.data.refresh_token);
          localStorage.setItem("member_number", res.data.data.member_number);
          localStorage.setItem("isSocialLogin", res.data.data.isSocialLogin);

          navigate("/main");
        } else {
          // 로그인이 실패한 경우 처리할 로직
          alert("다시 시도해주세요!");
        }
      })
      .catch((err) => {
        // 오류 처리
        console.log(err);
        alert("유효하지 않습니다. 다시 확인해주세요 !");
      });
  }, []); // 빈 배열을 전달하여 컴포넌트가 마운트될 때만 useEffect가 실행되도록 설정합니다.

  return <div>로그인중입니다...어쩌구</div>;
}
