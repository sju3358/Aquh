
import classes from "./NaverLoginButton.module.css";

export default function GoogleLoginButton({}) {

   
  const GOOGLE_AUTH_URL = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${process.env.REACT_APP_GOOGLE_CLIENT_ID}&redirect_uri=${process.env.REACT_APP_LOGIN_SERVER_URL}/redirectG&response_type=code&scope=profile email`;

  const handleNaverLogin = () => {
    window.location.href = GOOGLE_AUTH_URL;
  };
  return (
    <button onClick={handleNaverLogin} className={classes.login}>
      <img
        src='../../naver-login-white.png'
        alt='naver-login'
        className={classes.naverLogin}
      />
    </button>
  );
}
