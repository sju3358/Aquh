import React, { useState, useEffect } from 'react';
import classes from './BubbleCard.module.css'
import Capsule from '../ui/Capsule';
import { bubbleList } from '../api/api.bubble_service';
import Button from '../ui/Button';

export default function BubbleCard({
  roomType = 0,
  eventTitle = "Untitled",
  eventDate = "Some Date..",
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
        <p>{eventDate}</p>
      </div>
      <div className="card-footer">
    
      </div>
      <Button onClick={onJoin} variant="regular" className="card-button">Join</Button>
    </div>
    
  )
}