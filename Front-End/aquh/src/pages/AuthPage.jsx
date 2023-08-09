import axios from "axios";
import { useState, setState, useEffect } from "react";
import {
  memberEmailState,
  memberIntroState,
  memberNicknameState,
  memberTypeState
}from '../store/loginUserInfoState'

import { useRecoilValue } from "recoil";
import { main } from "@popperjs/core";
import classes from "./AuthPage.module.css";
import SymbolList from "../components/users/SymbolList";
import { memberNumberState } from '../store/loginUserState'
import { useNavigate } from "react-router";
import { useParams } from "react-router-dom";  
import UserSymbolList from "../components/users/UserSymbolList";


export default function AuthPage() {

  // params로 다른 주소 접근시 방지하기 위한 코드
  const memberNumber = useRecoilValue(memberNumberState);
  const navigate = useNavigate();
  const userId = useParams();

  
  useEffect(() => {
    let urlMemberNumber = userId.userId;
    console.log(urlMemberNumber,memberNumber);
    if(memberNumber != urlMemberNumber)
      navigate('/notfound')
  },[]);

  const memberEmail = useRecoilValue(memberEmailState);
  const memberNickname =useRecoilValue(memberNicknameState);
  const memberType = useRecoilValue(memberTypeState);
  const memberIntro = useRecoilValue(memberIntroState);


  return (
    <main className={classes.symbolSection}>
      <img src="../../avatar-image-circle.png" alt="" className={classes.profileAvatar} />
      <p classes={classes.profileNickname}>{memberNickname}</p>
      <UserSymbolList />
      <p>심볼 목록</p>
      <div className={classes.symbolContainer}>
      <SymbolList />
      </div>
    </main>
  );
}


