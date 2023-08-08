import axios from "axios";
import React, { useState, setState, useEffect } from "react";
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

function AuthPage() {
  
  
  const memberEmail = useRecoilValue(memberEmailState);
  const memberNickname =useRecoilValue(memberNicknameState);
  const memberType = useRecoilValue(memberTypeState);
  const memberIntro = useRecoilValue(memberIntroState);


  return (
    <main className={classes.symbolSection}>
      <img src="../../avatar-image-circle.png" alt="" className={classes.profileAvatar} />
      <p classes={classes.profileNickname}>{memberNickname}</p>
      <p>심볼 목록</p>
      <div className={classes.symbolContainer}>
        <SymbolList />
      </div>
    </main>
  );
}

export default AuthPage;
