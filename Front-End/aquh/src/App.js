import React from 'react';
import './App.css';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Root from './pages/Root';
import MainPage from './pages/MainPage';
import LoginModal from './pages/LoginModal';
import NotFound from './ui/NotFound';
import AuthPage from './pages/AuthPage';
import SignUpForm from './users/SignUpForm';
import BubblePage from './pages/BubblePage';  

const router = createBrowserRouter([
  {
    path: '/',
    element : <Root />,
    errorElement : <NotFound />,
    children : [
      { index: true, element : <MainPage /> },
      { path: '/login', element : <LoginModal />},
      { path: '/auth', element : <AuthPage />},
      // TODO : SignupForm을 SignUpPage로 바꾸기. 
      { path: '/signup', element : <SignUpForm />},
      { path: '/bubble', element : <BubblePage />},

    ]
  },
]);

export default function App() {
  return <RouterProvider router={router} />;
}

