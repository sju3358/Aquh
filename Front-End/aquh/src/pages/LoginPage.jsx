import React from 'react';
import classes from './LoginPage.module.css';
import NaverLoginButton from '../components/users/NaverLoginButton';

export default function LoginPage() {
  return (
    <section className={classes.container}>
      <div className={classes.loginBox}>
      <img src="../../foruth-image.png" alt="" className={classes.loginImg}/>
      <NaverLoginButton />
      </div>
    </section>
  );
}

