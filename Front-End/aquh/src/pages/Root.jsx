import React from "react";
import { Outlet, useNavigate } from "react-router-dom";
import Nav from "../components/ui/Nav";
import { useEffect } from "react";
import { memberNumberState } from "../store/loginUserState";
import { useRecoilValue } from "recoil";

export default function Root() {
  const memberNumber = useRecoilValue(memberNumberState);
  const navigate = useNavigate();

  useEffect(() => {
    if (memberNumber === -1) {
      alert("로그인이 필요합니다");
      navigate("/main");
    }
  }, []);

  return (
    <div>
      <Nav />
      <Outlet />
    </div>
  );
}
