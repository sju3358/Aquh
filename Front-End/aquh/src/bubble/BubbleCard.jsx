import React, { useState, useEffect } from 'react';
import classes from './BubbleCard.module.css'
import Capsule from '../ui/Capsule';
import { bubbleList } from '../api/api.bubble_service';
import Button from '../ui/Button';

export default function BubbleCard({
  roomType = 0,
  eventTitle = "Untitled",
  eventContent = "Some content...",
  imagePath,
  onJoin = () => {}
}) {

  // So just a few minor changes here
  return (
    <div className={classes.card}>
      {imagePath && <img className={classes.cardImgTop} src={imagePath} alt="Event" />}
      <div className="card-header">
        {/* TODO: roomType */}
        {/* <Capsule /> */}
        <h2>{eventTitle}</h2>
      </div>
      <div className="card-body">
        <p>{eventContent}</p>
      </div>
      <div className="card-footer">
        <p>Event Date</p>
      </div>
      <Button onClick={onJoin} variant="regular" className="card-button">Join</Button>
      { /* Ah see you wrote it with the label between the tags at first. That will work now! */}
      {/* so is it working rn? Wait one question...why does the BubbleCard have a label prop? */}
      {/* we dont need it? The button always says "Join" right? ahh right . okkee I will delete it then */}
      {/* "regular" is the default but I am going to put it anyway so it's more clear */}
    </div>
    
  )
}