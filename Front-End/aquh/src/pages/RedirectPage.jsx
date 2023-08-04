import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import https from "../utils/https";

import {
  memberEmailState,
  memberIntroState,
  memberNicknameState,
  memberTypeState
}from '../store/loginUserInfoState'

import {
  accessTokenState,
  refreshTokenState,
  memberNumberState,
  isSocialLoginState
}from '../store/loginUserState'


import { useSetRecoilState } from "recoil";

function RedirectPage() {

  const navigate = useNavigate();

  const setMemberEmail = useSetRecoilState(memberEmailState);
  const setMemberNickname =useSetRecoilState(memberNicknameState);
  const setMemberType= useSetRecoilState(memberTypeState);
  const setMemberIntro = useSetRecoilState(memberIntroState);

  const setAccessToken = useSetRecoilState(accessTokenState);
  const setRefreshToken = useSetRecoilState(refreshTokenState);
  const setMemberNumber = useSetRecoilState(memberNumberState);
  const setIsSocialLogin = useSetRecoilState(isSocialLoginState);

  

  useEffect(() => {

    let code = new URL(window.location.href).searchParams.get("code");
    let state = new URL(window.location.href).searchParams.get("state");

    https
      .post("/api/v1/member/auth/naver", {
        code: code,
        state: state,
      })
      .then((res) => {
        if (res.status === 200) {

          localStorage.setItem("access_token", res.data.data.access_token);
          localStorage.setItem("refresh_token", res.data.data.refresh_token);
          localStorage.setItem("member_number", res.data.data.member_number);
          localStorage.setItem("isSocialLogin", res.data.data.isSocialLogin);

          setAccessToken(res.data.data.access_token);
          setRefreshToken(res.data.data.refresh_token)
          setMemberNumber(res.data.data.member_number);
          setIsSocialLogin(res.data.data.isSocialLogin);

          https.get(`/api/v1/member/${res.data.data.member_number}`).then((res) => {

            setMemberNickname(res.data.data.member_nickname);
            setMemberType(res.data.data.member_type);
            setMemberIntro(res.data.data.member_intro);
            setMemberEmail(res.data.data.member_email);

          });
          navigate("/");
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

export default RedirectPage;