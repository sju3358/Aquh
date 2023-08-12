import React, { useState } from "react";
import classes from "./NicknamePage.module.css";
import axios from "axios";
import https from "../utils/https";

function NicknamePage() {
  const [nickName, setNickname] = useState();
  // 유효성검사
  const [isNickName, setIsNickName] = useState(false);
  // 유효성검사 메시지 발송
  const [nickNameMessage, setNickNameMessage] = useState();
  // 중복체크를 통한 가입 가능 여부
  const [vaildNickName, setValidNickName] = useState();

  const onChangeNickName = (e) => {
    const currentUserName = e.target.value;

    setNickname(currentUserName);

    const usernameRegExp = /^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{3,10}$/;
    if (!usernameRegExp.test(currentUserName)) {
      setNickNameMessage(
        "닉네임은 특수문자 없이 3~10글자 사이로 입력해주세요!"
      );
      setIsNickName(false);
    } else {
      setNickNameMessage("사용가능한 닉네임 입니다.");
      setIsNickName(true);
    }
  };

  // 닉네임 중복체크
  const nickNameCheckAxios = (e) => {
    https
      .post("/api/v1/member/check/nickname", {
        member_nickname: nickName,
      })
      .then((res) => {
        console.log(res);
        const isValidNick = res.data.isExistSameNickname; //응답이 제대로 온 경우

        if (!isValidNick) {
          // 중복체크 통과 로직
          setValidNickName(isValidNick); //true 값으로 바뀜
          alert("사용 가능한 닉네임 입니다!");
        } else {
          // 중복된 닉네임인 경우 로직
          alert("이미 존재하는 닉네임입니다!");
          setNickname("");
        }
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
            입장하기 전, 방울의 이름을 지어 주세요.
          </p>
        </div>
        <div className={classes.nicknameSection}>
          <input
            className={classes.nicknameInput}
            type='text'
            name='nickName'
            value={nickName}
            id='nickName'
            placeholder='불리고 싶은 이름을 적어주세요'
            onChange={onChangeNickName}
          />
          {/* TODO: 유효성검사, 중복확인 로직 필요 */}
          {isNickName ? (
            <button
              onClick={nickNameCheckAxios}
              className={classes.nicknameCheck}>
              중복확인
            </button>
          ) : (
            <button disabled>중복확인</button>
          )}
          <p className='message'>{nickNameMessage}</p>
        </div>
        {/* TODO: 입장하기 클릭-->닉네임 변경 axios.  한번 날리고 (maybe 회원정보 수정?)  성공하면 then (~ 이 안에서 두번째 axios.post->이메일 인증 요청 페이지(로그인 관문 3)-> 성공하면 ㅔㅔ네비게이트 메인페이지 */}
        <button className={classes.enterBtn}>입장하기</button>
      </div>
    </div>
  );
}
export default NicknamePage;
