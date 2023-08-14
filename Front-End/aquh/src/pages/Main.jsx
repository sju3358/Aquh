import React from "react";
import { Outlet, useNavigate } from "react-router-dom";
import Nav from "../components/ui/Nav";

export default function Main() {
  return (
    <div>
      <Nav />
      <Outlet />
    </div>
  );
}
