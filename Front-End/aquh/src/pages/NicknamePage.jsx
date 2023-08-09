import React from "react";
import classes from "./NicknamePage.module.css";

function NicknamePage() {
  return (
    <div className={classes.container}>
      <div className={classes.infoText}>
        <img src='../../aquh3.png' alt='' className={classes.loginImg} />
        <p>Aquh에 오신것을 환영합니다!</p>
        <h3 className={classes.infoTextSmall}>
          입장하기 전, 닉네임을 설정 해 주세요.
        </h3>
      </div>
      <input
        className={classes.nicknameInput}
        type='text'
        name='nickName'
        id='nickName'
        placeholder='닉네임을 적어주세요.'
      />
      {/* TODO: 유효성검사, 중복확인 로직 필요 */}
      <button className={classes.nicknameCheck}>중복확인</button>
      {/* TODO: 입장하기 클릭-->닉네임 변경 axios.  한번 날리고 (maybe 회원정보 수정?)  성공하면 then (~ 이 안에서 두번째 axios.post->이메일 인증 요청 페이지(로그인 관문 3)-> 성공하면 ㅔㅔ네비게이트 메인페이지 */}
      <button className={classes.enterBtn}>입장하기</button>
    </div>
  );
}
export default NicknamePage;
