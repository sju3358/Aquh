import React from 'react';
import classes from './ChattingAvatarImg.module.css';

export default function ChattingAvatarImg({level}) {
  return (
    <div>
      <img src={`../../chat-profile${level}.png`} alt="AvatarImg" className={classes.profileAvatar}/>
    </div>
  );
}

