import React from 'react';
import { Outlet, useNavigate } from 'react-router-dom';
import Nav from '../components/ui/Nav';
import { useEffect } from 'react';
import {
  accessTokenState,
}from '../store/loginUserState'
import { useRecoilValue } from 'recoil';


export default function Root() {

  const accessToken = useRecoilValue(accessTokenState);
  const navigate = useNavigate();

  useEffect(()=>{
    if(accessToken === ""){
      alert("로그인이 필요합니다");
      navigate("/login");
    }
  })

  

  return (
    <div>
      <Nav />
      <Outlet />
    </div>
  );
}

