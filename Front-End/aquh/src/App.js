import React from "react";
import "./App.css";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Root from "./pages/Root";
import MainPage from "./pages/MainPage";
// import LoginModal from './pages/LoginModal';
import NotFound from "./components/ui/NotFound";
import AuthPage from "./pages/AuthPage";
import SignUpForm from "./components/users/SignUpForm";
import BubblePage from "./pages/BubblePage";
import RedirectPage_Naver from "./pages/RedirectPage_Naver";
import RedirectPage_Google from "./pages/RedirectPage_Google";
import LoginPage from "./pages/LoginPage";
import FeedPage from "./pages/FeedPage";
import VideoPage from "./pages/VideoPage";
import NicknamePage from "./pages/NicknamePage";
import Modal from "react-modal";
import BubbleDetailPage from "./pages/BubbleDetailPage";
import LogoutPage from "./pages/LogoutPage";
import IntroPage from "./pages/IntroPage";
// import Test from "./Test";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Root />,
    errorElement: <NotFound />,
    children: [
      { path: "/main", element: <MainPage /> },
      { path: "/auth/:userId", element: <AuthPage /> },
      // TODO : SignupForm을 SignUpPage로 바꾸기.
      { path: "/feed", element: <FeedPage /> },
      { path: "/bubble", element: <BubblePage /> },

      { path: "/video/:id", element: <VideoPage /> },
    
    ],
  },
  {
    path: "/bubble/:id",
    element: <BubbleDetailPage />,
    errorElement: <NotFound />,
  },
  {
    path: "/login",
    element: <LoginPage />,
    errorElement: <NotFound />,
  },
  {
    path: "/logout",
    element: <LogoutPage />,
    errorElement: <NotFound />,
  },
  {
    path: "/redirectG",
    element: <RedirectPage_Google />,
    errorElement: <NotFound />,
  },
  {
    path: "/redirectN",
    element: <RedirectPage_Naver />,
    errorElement: <NotFound />,
  },
  { path: "/nickname", element: <NicknamePage />, errorElement: <NotFound />,}
]);

export default function App() {
  Modal.setAppElement("#root");
  return <RouterProvider router={router} />;
}
