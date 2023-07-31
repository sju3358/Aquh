import React, { useState, setState } from "react";
import axios from "axios";
import classNames from "classnames";
import "./SignUpForm.css";

function RegisterForm() {
  // 기본 세팅
  const [username, setUserName] = useState("");
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setPasswordConfirm] = useState("");

  // 유효성 검사
  const [isName, setIsName] = useState(false);
  const [isUserName, setIsUserName] = useState(false);
  const [isEmail, setIsEmail] = useState(false);
  const [isPassword, setIsPassword] = useState(false);
  const [isPasswordConfirm, setIsPasswordConfirm] = useState(false);

  // 중복체크를 통한 회원가입 가능 여부
  const [validUserName, setValidUserName] = useState(false);
  const [validEmail, setValidEmail] = useState(false);

  // 유효성 검사를 통한 메시지 발송
  const [nameMessage, setNameMessage] = useState("");
  const [userNameMessage, setUserNameMessage] = useState("");
  const [passwordMessage, setPasswordMessage] = useState("");
  const [passwordConfirmMessage, setPasswordConfirmMessage] = useState("");
  const [emailMessage, setEmailMessage] = useState("");

  // 유효성 검사 여부를 통한 버튼 차이주기
  const ValidSignUp = classNames({
    ["cantSignUp"]: true,
    ["canSignUp"]: validUserName && validEmail && isPasswordConfirm && isName,
  });

  // 메시지 내용

  const onChangeName = (e) => {
    const currentName = e.target.value;
    setName(currentName);

    const nameRegExp = /^[ㄱ-ㅎ가-힣a-zA-Z]{2,8}$/;
    if (!nameRegExp.test(currentName)) {
      setNameMessage("이름은 2~8글자 사이로 입력해주세요!");
      setIsName(false);
    } else {
      setNameMessage("사용가능한 이름입니다.");
      setIsName(true);
    }
  };

  const onChangeUserName = (e) => {
    const currentUserName = e.target.value;

    setUserName(currentUserName);

    const usernameRegExp = /^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{3,10}$/;
    if (!usernameRegExp.test(currentUserName)) {
      setUserNameMessage(
        "닉네임은 특수문자 없이 3~10글자 사이로 입력해주세요!"
      );
      setIsUserName(false);
    } else {
      setUserNameMessage("사용가능한 닉네임 입니다.");
      setIsUserName(true);
    }
  };

  const onChangePassword = (e) => {
    const currentPassword = e.target.value;
    setPassword(currentPassword);
    const passwordRegExp =
      /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/;
    if (!passwordRegExp.test(currentPassword)) {
      setPasswordMessage(
        "숫자+영문자+특수문자 조합으로 8자리 이상 입력해주세요."
      );
      setIsPassword(false);
    } else {
      setPasswordMessage("안전한 비밀번호 입니다.");
      setIsPassword(true);
    }
  };

  const onChangePasswordConfirm = (e) => {
    const currentPasswordConfirm = e.target.value;
    setPasswordConfirm(currentPasswordConfirm);
    if (password !== currentPasswordConfirm) {
      setPasswordConfirmMessage("비밀번호가 일치하지 않습니다.");
      setIsPasswordConfirm(false);
    } else {
      setPasswordConfirmMessage("비밀번호가 일치합니다.");
      setIsPasswordConfirm(true);
    }
  };

  const onChangeEmail = (e) => {
    const currentEmail = e.target.value;
    setEmail(currentEmail);
    const emailRegExp =
      /^[A-Za-z0-9_]+[A-Za-z0-9]*[@]{1}[A-Za-z0-9]+[A-Za-z0-9]*[.]{1}[A-Za-z]{1,3}$/;

    if (!emailRegExp.test(currentEmail)) {
      setEmailMessage("이메일의 형식이 올바르지 않습니다!");
      setIsEmail(false);
    } else {
      setEmailMessage("사용 가능한 이메일 입니다.");
      setIsEmail(true);
    }
  };
  // 닉네임 중복체크
  const nickNameCheckAxios = (e) => {
    axios
      .post("/api/v1/member/check/nickname", {
        member_nickname: username,
      })
      .then((res) => {
        const isValidNick = res.data.isExistSameNickname; //응답이 제대로 온 경우

        if (isValidNick) {
          // 중복체크 통과 로직
          setValidUserName(isValidNick); //true 값으로 바뀜
          alert("사용 가능한 닉네임 입니다!");
        } else {
          // 중복된 닉네임인 경우 로직
          alert("이미 존재하는 닉네임입니다!");
          setUserName("");
        }
      });
  };
  // 이메일 중복체크
  const emailCheckAxios = (e) => {
    axios
      .post("/api/v1/member/check/email", {
        member_email: email,
      })
      .then((res) => {
        const isValidEmail = res.data.isExistSameEmail;

        if (isValidEmail) {
          //이메일 중복체크 통과 로직
          setValidEmail(isValidEmail); //true 값으로 바뀜
          alert("중복체크 완료!");
        } else {
          // 중복된 이메일인 경우 로직
          alert("이미 존재하는 이메일 입니다.");
          setIsEmail("");
        }
      });
  };
  // 회원가입 가능 여부
  const signUpFunc = () => {
    // 3가지 조건을 모두 확인
    if (validUserName && validEmail && isPasswordConfirm) {
      // axios 보내기 -> 회원정보 등록 api
      axios
        .post("/api/v1/member", {
          member_email: email,
          member_nickname: username,
          member_password: password,
          member_password_repeat: confirmPassword,
        })
        .then((res) => {
          // 이메일 발송하기
          console.log(res);
          alert("24시간 이내에 회원님의 이메일에서 인증을 완료 해 주세요 !");
          // 이거 이후에 어떻게 할건지? 메인페이지로 보내기?
        });
    } else {
      // alert("작성하신 내용을 다시 확인 해 주세요!");
      return;
    }
  };

  return (
    <div>
      <h3>SignUp</h3>
      <>
        <div className='name'>
          <label htmlFor='name'>Name : </label>
          <input type='text' value={name} id='name' onChange={onChangeName} />
          <p className='message'>{nameMessage}</p>
        </div>
        <div className='username'>
          <label htmlFor='username'>Username : </label>
          <input
            type='text'
            value={username}
            id='username'
            onChange={onChangeUserName}
          />
          {isUserName ? (
            <button onClick={nickNameCheckAxios}>중복확인</button>
          ) : (
            <button disabled>중복확인</button>
          )}

          <p className='message'>{userNameMessage}</p>
          {/* <input type="text"  id="username"/> */}
        </div>
        <div className='email'>
          <label htmlFor='email'>Email : </label>
          <input
            type='email'
            value={email}
            id='email'
            onChange={onChangeEmail}
          />
          {isEmail ? (
            <button onClick={emailCheckAxios}>중복확인</button>
          ) : (
            <button disabled>중복확인</button>
          )}

          <p className='message'>{emailMessage}</p>
          {/* <input type="email"  id="email"/> */}
        </div>
        <div className='password'>
          <label htmlFor='password'>Password : </label>

          <input
            type='password'
            value={password}
            id='password'
            name='password'
            onChange={onChangePassword}
          />
          <p className='message'>{passwordMessage}</p>
          {/* <input type="password"  id ="password" /> */}
        </div>
        <div className='confirmPassword'>
          <label htmlFor='confirmPassword'>Confirm Password : </label>
          <input
            type='password'
            value={confirmPassword}
            id='confirmPassword'
            onChange={onChangePasswordConfirm}
          />
          <p className='message'>{passwordConfirmMessage}</p>
          {/* <input type="password"  id="confirmPassword" /> */}
        </div>
      </>
      {validUserName && validEmail && isPasswordConfirm && isName ? (
        <button onClick={signUpFunc} className={ValidSignUp} type='submit'>
          Sign Up
        </button>
      ) : (
        <button disabled className={ValidSignUp} type='submit'>
          Sign Up
        </button>
      )}
    </div>
  );
}
export default RegisterForm;
