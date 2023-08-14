import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import https from "../utils/https";

import {
  memberEmailState,
  memberIntroState,
  memberNicknameState,
  memberTypeState,
  memberActiveSymbolState,
} from "../store/loginUserInfoState";

import { memberNumberState, isSocialLoginState } from "../store/loginUserState";

import { useResetRecoilState } from "recoil";

export default function LogoutPage() {
  const navigate = useNavigate();

  const resetMemberEmail = useResetRecoilState(memberEmailState);
  const resetMemberNickname = useResetRecoilState(memberNicknameState);
  const resetMemberType = useResetRecoilState(memberTypeState);
  const resetMemberIntro = useResetRecoilState(memberIntroState);

  const resetMemberNumber = useResetRecoilState(memberNumberState);
  const resetIsSocialLogin = useResetRecoilState(isSocialLoginState);
  const resetMemberActiveSymbol = useResetRecoilState(memberActiveSymbolState);

  useEffect(() => {
    https
      .delete("/api/v1/member/auth")
      .then(() => {
        localStorage.removeItem("access_token");
        localStorage.removeItem("refresh_token");
        resetMemberEmail();
        resetMemberNickname();
        resetMemberType();
        resetMemberIntro();

        resetMemberNumber();
        resetIsSocialLogin();
        resetMemberActiveSymbol();
        navigate("/main");
      })
      .catch((error) => {
        console.log(error);
        alert("다시 시도해주세요!");
        navigate("/main");
      });
  });

  return <div></div>;
}
