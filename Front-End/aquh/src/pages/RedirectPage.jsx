import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import https from "../utils/https";

import {
  memberEmailState,
  memberIntroState,
  memberNicknameState,
  memberTypeState
}from '../store/loginUserInfoState';

import {
  accessTokenState,
  refreshTokenState,
  memberNumberState,
  isSocialLoginState
}from '../store/loginUserState';

import { useSetRecoilState } from "recoil";

export default function RedirectPage() {
  
  const navigate = useNavigate();

  const setMemberEmail = useSetRecoilState(memberEmailState);
  const setMemberNickname =useSetRecoilState(memberNicknameState);
  const setMemberType= useSetRecoilState(memberTypeState);
  const setMemberIntro = useSetRecoilState(memberIntroState);
  
  const setMemberNumber = useSetRecoilState(memberNumberState);
  const setIsSocialLogin = useSetRecoilState(isSocialLoginState);


  useEffect( () => {

    let code = new URL(window.location.href).searchParams.get("code");
    let state = new URL(window.location.href).searchParams.get("state");

    const data = {
      code: code,
      state: state,
    };
    https.post("/api/v1/member/auth/naver",data)
    .then( (res) => {
      if (res.status === 200) {

          localStorage.setItem("access_token", res.data.data.access_token);
          localStorage.setItem("refresh_token", res.data.data.refresh_token);

          setMemberNumber(res.data.data.member_number);
          setIsSocialLogin(res.data.data.isSocialLogin);

          https.get(`/api/v1/member/${res.data.data.member_number}`)
          .then((res) => {

            setMemberNickname(res.data.data.member_nickname);
            setMemberType(res.data.data.member_type);
            setMemberIntro(res.data.data.member_intro);
            setMemberEmail(res.data.data.member_email);

          });

          navigate("/");
          
      } else {
         
          alert("다시 시도해주세요!");
          navigate("/login");

      }})
    .catch((error) => {
      console.log(error);
      alert("다시 시도해주세요!");
      navigate("/login");

    });
  });

  return (<div>로그인중입니다...어쩌구</div>);
}
