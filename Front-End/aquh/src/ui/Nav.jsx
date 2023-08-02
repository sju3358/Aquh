import React from 'react';
import { Link } from 'react-router-dom';

export default function Nav() {
  return (
    <nav>
      <Link to='/'> Main </Link>
      <Link to='/auth'> Auth </Link> | 
      <Link to='/signup'> SignUP </Link> |
      <Link to='/profile'> Profile </Link> |
      <Link to='/group'> Group </Link> |
      <Link to='/login'> Login </Link> |
      <Link to='/feed'> Feed </Link> |       
    </nav>
  );
}

