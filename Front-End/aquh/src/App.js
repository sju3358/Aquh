import React from 'react';
import './App.css';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Root from './pages/Root';
import MainPage from './pages/MainPage';
// import LoginModal from './pages/LoginModal';
import NotFound from './components/ui/NotFound';
import AuthPage from './pages/AuthPage';
import SignUpForm from './components/users/SignUpForm';
import BubblePage from './pages/BubblePage';  
import RedirectPage from './pages/RedirectPage'
import LoginPage from './pages/LoginPage';
import FeedPage from './pages/FeedPage';
import VideoPage from './pages/VideoPage';
const router = createBrowserRouter([
  // { path: '/login',
  //   element : <LoginPage />,
  //   errorElement : <NotFound />,
  // },
  {
    path: '/',
    element : <Root />,
    errorElement : <NotFound />,
    children : [
      { index: 'true', element : <MainPage /> },
      // { path: '/login', element : <LoginPage />},
      { path: '/auth', element : <AuthPage />},
      // TODO : SignupForm을 SignUpPage로 바꾸기. 
      { path: '/feed', element : <FeedPage />},
      { path: '/bubble', element : <BubblePage />},
      // 0807 김재원 수정
      { path: '/video', element : <VideoPage />},
      
    ]
  },
  {
    path:'/redirect',
    element : <RedirectPage/>,
    errorElement : <NotFound />,
  }

]);

export default function App() {
  return <RouterProvider router={router} />;
}

