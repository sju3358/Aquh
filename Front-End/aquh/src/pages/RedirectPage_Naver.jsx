import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import https from "../utils/https";
import classes from "./RedirectPage_Naver.module.css";

import {
  memberEmailState,
  memberIntroState,
  memberNicknameState,
  memberTypeState,
  memberLevelState,
} from "../store/loginUserInfoState";

import {
  accessTokenState,
  refreshTokenState,
  memberNumberState,
  isSocialLoginState,
} from "../store/loginUserState";

import { useSetRecoilState } from "recoil";
import axios from "axios";

export default function RedirectPage_Naver() {
  const navigate = useNavigate();

  const setMemberEmail = useSetRecoilState(memberEmailState);
  const setMemberNickname = useSetRecoilState(memberNicknameState);
  const setMemberType = useSetRecoilState(memberTypeState);
  const setMemberIntro = useSetRecoilState(memberIntroState);

  const setMemberNumber = useSetRecoilState(memberNumberState);
  const setIsSocialLogin = useSetRecoilState(isSocialLoginState);
  const setMemberLevel= useSetRecoilState(memberLevelState);

  useEffect(() => {
    let code = new URL(window.location.href).searchParams.get("code");
    let state = new URL(window.location.href).searchParams.get("state");

    const data = {
      code: code,
      state: state,
    };

    console.log(data);
    https
      .post("/api/v1/member/auth/naver", data)
      .then((res) => {
        if (res.status === 200) {
          localStorage.setItem("access_token", res.data.data.access_token);
          localStorage.setItem("refresh_token", res.data.data.refresh_token);

          setMemberNumber(res.data.data.member_number);
          setIsSocialLogin(res.data.data.isSocialLogin);

          let memberState = -1;
          https.get(`/api/v1/member`).then((res) => {
            setMemberNickname(res.data.data.member_nickname);
            setMemberType(res.data.data.member_type);
            setMemberIntro(res.data.data.member_intro);
            setMemberEmail(res.data.data.member_email);
            setMemberLevel(res.data.data.member_level);
            memberState = res.data.data.member_state;
            if (memberState === 0) navigate("/nickname");
            else if (memberState === 1) navigate("/main");
          });
        } else {
          alert("다시 시도해주세요!");
          navigate("/login");
        }
      })
      .catch((error) => {
        console.log(error);
        alert("다시 시도해주세요!");
        navigate("/login");
      });
  });

  return (
    <></>
  );
}
