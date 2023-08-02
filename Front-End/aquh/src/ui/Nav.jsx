import React from 'react';
import { Link } from 'react-router-dom';

export default function Nav() {
  return (
    <nav>
      <Link to='/'> Main </Link> |
      <Link to='/auth'> Auth </Link> | 
      <Link to='/signup'> SignUp </Link> |
      <Link to='/profile'> Profile </Link> |
      <Link to='/login'> Login </Link> |
      <Link to='/feed'> Feed </Link> | 
      <Link to='/bubble'> Bubble </Link> |         
    </nav>
  );
}

