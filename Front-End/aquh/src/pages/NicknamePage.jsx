import React, { useState } from "react";
import classes from "./NicknamePage.module.css";
import https from "../utils/https";
import { useNavigate } from "react-router";
import { useRecoilState, useRecoilValue } from "recoil";
import { memberNicknameState } from "../store/loginUserInfoState";

import { memberNumberState } from "../store/loginUserState";

function NicknamePage() {
  const [currentNickName, setCurrentNickname] =
    useRecoilState(memberNicknameState);
  const [inputNickname, setInputNickname] = useState(currentNickName);
  const [inputNicknameMessage, setInputNicknameMessage] = useState(
    "닉네임은 특수문자 없이 3~10글자 사이로 입력해주세요!"
  );
  const [nickNameRegexCheck, setNickNameRegexCheck] = useState(false);
  const [nickNameDuplicationCheck, setNickNameDuplicationCheck] =
    useState(false);

  const navigate = useNavigate();

  const memberNumber = useRecoilValue(memberNumberState);

  // 닉네임 유효성검사
  const onChangeNickName = (e) => {
    const inputNickname = e.target.value;
    const usernameRegExp = /^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{3,10}$/;
    setInputNickname(inputNickname);

    if (usernameRegExp.test(inputNickname)) {
      setInputNicknameMessage("사용 가능한 닉네임 입니다.");
      setNickNameRegexCheck(true);
    } else {
      setInputNicknameMessage(
        "닉네임은 특수문자 없이 3~10글자 사이로 입력해주세요!"
      );
      setNickNameRegexCheck(false);
    }
  };

  // 닉네임 중복체크
  const nickNameCheckAxios = (e) => {
    https
      .post("/api/v1/member/check/nickname", {
        member_nickname: inputNickname,
      })
      .then((res) => {
        console.log(res);

        const isValidNickName = res.data.isExistSameNickname === false;

        if (isValidNickName) {
          setNickNameDuplicationCheck(true);
          alert("사용 가능한 닉네임 입니다!");
        } else {
          setNickNameDuplicationCheck(false);
          alert("이미 존재하는 닉네임입니다!");
        }
      });
  };

  // 입장하기 버튼 클릭 시 회원정보 변경
  const enterMainPage = () => {
    https
      .put("/api/v1/member", {
        member_nickname: inputNickname,
        //TODO : recoil atom에 있는 닉넴도 변경해줘야 하지않나?
      })
      .then((res) => {
        https
          .get(`/api/v1/member/url/state-certification/${memberNumber}`)
          .then(() => {
            alert("환영합니다");
            setCurrentNickname(inputNickname);
            navigate("/main");
          })
          .catch((error) => {
            console.log(error);
          });
      })
      .catch((error) => {
        console.log(error);
      });
  };
  return (
    <div className={classes.nicknameCheck}>
      <div className={classes.container}>
        <img src='../../aquh3.png' alt='' className={classes.nicknameImg} />{" "}
        <div className={classes.infoText}>
          <h1 className={classes.infoTextDetail}>
            Aquh에 오신것을 환영합니다!
          </h1>
          <p className={classes.infoTextDetailSmall}>
            입장하기 전, 캐릭터의 이름을 지어 주세요.
          </p>
        </div>
        <div className={classes.nicknameSection}>
          <input
            className={classes.nicknameInput}
            type='text'
            name='nickName'
            value={inputNickname}
            id='nickName'
            placeholder='불리고 싶은 이름을 적어주세요'
            onChange={onChangeNickName}
          />
          {/* TODO: 유효성검사, 중복확인 로직 필요 */}
          {nickNameRegexCheck === true ? (
            <button
              onClick={nickNameCheckAxios}
              className={classes.nicknameCheckBtn}>
              중복확인
            </button>
          ) : (
            <button className={classes.nicknameCheckBtnDisable} disabled>
              중복확인
            </button>
          )}
          <p className='message'>{inputNicknameMessage}</p>
        </div>
        {nickNameRegexCheck === true && nickNameDuplicationCheck === true ? (
          <button onClick={enterMainPage} className={classes.enterBtn}>
            입장하기
          </button>
        ) : (
          <button disabled className={classes.enterBtnDisabled}>
            입장하기
          </button>
        )}
      </div>
    </div>
  );
}
export default NicknamePage;
