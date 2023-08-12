import React from 'react';
import classes from './AvatarImg.module.css';

export default function AvatarImg({level}) {
  return (
    <div>
      <img src={`../../pfp${level}.png`} alt="AvatarImg" className={classes.profileAvatar}/>
    </div>
  );
}

