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
import RedirectPage from "./pages/RedirectPage";
import LoginPage from "./pages/LoginPage";
import FeedPage from "./pages/FeedPage";
import VideoPage from "./pages/VideoPage";
import NicknamePage from "./pages/NicknamePage";
import Modal from "react-modal";
import BubbleDetailPage from "./pages/BubbleDetailPage";
// import Test from "./Test";

const router = createBrowserRouter([
  // { path: '/login',
  //   element : <LoginPage />,
  //   errorElement : <NotFound />,
  // },

  {
    path: "/",
    element: <Root />,
    errorElement: <NotFound />,
    children: [
      { index: "true", element: <MainPage /> },
      { path: "/login", element: <LoginPage /> },
      { path: "/auth/:userId", element: <AuthPage /> },
      // TODO : SignupForm을 SignUpPage로 바꾸기.
      { path: "/feed", element: <FeedPage /> },
      { path: "/bubble", element: <BubblePage /> },
      { path: "/bubble/:id", element: <BubbleDetailPage />},
      // 0807 김재원 수정
      { path: "/video", element: <VideoPage /> },
      { path: "/nickname", element: <NicknamePage /> },
  
    ],
  },
  {
    path: "/redirect",
    element: <RedirectPage />,
    errorElement: <NotFound />,
  },
  // { path: "/test", element: <Test /> },
]);

export default function App() {
  Modal.setAppElement("#root");
  return <RouterProvider router={router} />;
}
