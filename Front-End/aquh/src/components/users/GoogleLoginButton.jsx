import classes from "./NaverLoginButton.module.css";

export default function GoogleLoginButton({}) {
  const GOOGLE_AUTH_URL = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${process.env.REACT_APP_GOOGLE_CLIENT_ID}&redirect_uri=${process.env.REACT_APP_LOGIN_SERVER_URL}/redirectG&response_type=code&scope=profile email`;

  const handleGoogleLogin = () => {
    window.location.href = GOOGLE_AUTH_URL;
  };
  return (
    <button onClick={handleGoogleLogin} className={classes.login}>
      <img
        src='../../구글로그인버튼.png'
        alt='google-login'
        className={classes.naverLogin}
      />
    </button>
  );
}
