import React from 'react';
import { Link } from 'react-router-dom';
import classes from './Nav.module.css';

export default function Nav() {
  return (
    <nav className={classes.navBar}>
      <Link to='/'> <img src="../../aquh-logo.png" alt="aquh-logo" className={classes.logoPng} /></Link>
      <div className={classes.navItemContainer}>
        <div>
          <Link to='/feed' style={{ textDecoration : "none" }}><span className={classes.navItem}>피드</span></Link>
          <Link to='/bubble' style={{ textDecoration : "none" }}><span className={classes.navItem}>버블</span></Link>
          <Link to='/profile' style={{ textDecoration : "none" }}><span className={classes.navItem}>My</span></Link>
        </div>
        <img src="../../avatar-image-circle.png" alt="search" className={classes.avatarPng} />  
      </div>     
    </nav>
  );
}

