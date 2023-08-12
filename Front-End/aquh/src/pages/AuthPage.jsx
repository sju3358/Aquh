import axios from "axios";
import { useState, setState, useEffect } from "react";
import {
  memberEmailState,
  memberIntroState,
  memberNicknameState,
  memberTypeState,
} from "../store/loginUserInfoState";

import { useRecoilValue } from "recoil";
import { main } from "@popperjs/core";
import classes from "./AuthPage.module.css";
import SymbolList from "../components/users/SymbolList";
import { memberNumberState } from "../store/loginUserState";
import { useNavigate } from "react-router";
import { useParams } from "react-router-dom";
import UserSymbolList from "../components/users/UserSymbolList";
import { fetchSingleUser } from "../utils/api/api.auth_service";
import UserFeedList from "../components/feed/UserFeedList";
import UserLevelCard from "../components/users/UserLevelCard";
import AvatarImg from "../components/ui/AvatarImg";

export default function AuthPage() {
  // params로 다른 주소 접근시 방지하기 위한 코드
  const memberNumber = useRecoilValue(memberNumberState);
  const navigate = useNavigate();
  const userId = useParams();
  const [user, setUser] = useState({
    level: 0,
    maxExp: 0,
    presentExp: 0,
    remainingExp: 0,
  });

  useEffect(() => {
    const getSingleUser = async () => {
      const response = await fetchSingleUser();
      try {
        const res = response.data.data;
        // console.log("가져왔다 유저정보보보보", res)
        setUser(res);
      } catch (error) {
        console.log("에러ㅏ에러에러", error);
      }
    };
    getSingleUser();
  }, []);

  useEffect(() => {
    let urlMemberNumber = userId.userId;
    if (memberNumber != urlMemberNumber) navigate("/notfound");
  }, [navigate, memberNumber, userId.userId]);

  const memberEmail = useRecoilValue(memberEmailState);
  const memberNickname = useRecoilValue(memberNicknameState);
  const memberType = useRecoilValue(memberTypeState);
  const memberIntro = useRecoilValue(memberIntroState);

  return (
    <main className={classes.symbolSection}>
      {/* <img src="../../avatar-image-circle.png" alt="" className={classes.profileAvatar} /> */}
      <AvatarImg level={user.level} />
      <UserLevelCard
        level={user.level}
        maxExp={user.maxExp}
        presentExp={user.presentExp}
        remainingExp={user.remainingExp}
      />
      <p className={classes.profileNickname}>{memberNickname}</p>
      {memberIntro ? (
        <div className={classes.memberIntro}>{memberIntro}</div>
      ) : (
        <div className={classes.memberIntro}>작성된 자기소개가 없습니다.</div>
      )}
      <UserSymbolList />
      {/* <button> 심볼 목록 보기 </button> */}
      {/* <p>심볼 목록</p> */}
      <div className={classes.symbolContainer}>
        <SymbolList />
      </div>
      <div>
        <UserFeedList />
      </div>
    </main>
  );
}
